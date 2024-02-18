package com.github.gerasimovnikita.otus.carsale.repo.sql

import com.benasher44.uuid.uuid4
import helpers.asCarSaleError
import models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import repo.*

class RepoAdSQL(
    properties: SqlProperties = SqlProperties(),
    initObjects: Collection<CarSaleAd> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
) : IAdRepository {

    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
        }

        Database.connect(
            properties.url, driver, properties.user, properties.password
        )

        transaction {
            if (properties.dropDatabase) SchemaUtils.drop(CarSaleAdTable)
            SchemaUtils.create(CarSaleAdTable)
            initObjects.forEach { createAd(it) }
        }
    }

    private fun createAd(ad: CarSaleAd): CarSaleAd {
        val res = CarSaleAdTable.insert {
            to(it, ad, randomUuid)
        }

        return CarSaleAdTable.from(res)
    }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbAdResponse): DbAdResponse =
        transactionWrapper(block) { DbAdResponse.error(it.asCarSaleError()) }

    override suspend fun createAd(rq: DbAdRequest): DbAdResponse = transactionWrapper {
        DbAdResponse.success(createAd(rq.ad))
    }

    private fun read(id: CarSaleAdId): DbAdResponse {
        val res = CarSaleAdTable.select {
            CarSaleAdTable.id eq id.asString()
        }.singleOrNull() ?: return DbAdResponse.errorNotFound
        return DbAdResponse.success(CarSaleAdTable.from(res))
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse = transactionWrapper { read(rq.id) }

    private fun update(
        id: CarSaleAdId,
        lock: CarSaleAdLock,
        block: (CarSaleAd) -> DbAdResponse
    ): DbAdResponse =
        transactionWrapper {

            if (id == CarSaleAdId.NONE) return@transactionWrapper DbAdResponse.errorEmptyId

            val current = CarSaleAdTable.select { CarSaleAdTable.id eq id.asString() }
                .firstOrNull()
                ?.let { CarSaleAdTable.from(it) }

            when {
                current == null -> DbAdResponse.errorNotFound
                current.lock != lock -> DbAdResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse =
        update(rq.ad.id, rq.ad.lock) {
            CarSaleAdTable.update({
                (CarSaleAdTable.id eq rq.ad.id.asString()) and (CarSaleAdTable.lock eq rq.ad.lock.asString())
            }) {
                to(it, rq.ad, randomUuid)
            }
            read(rq.ad.id)
        }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse = update(rq.id, rq.lock) {
        CarSaleAdTable.deleteWhere {
            (CarSaleAdTable.id eq rq.id.asString()) and (CarSaleAdTable.lock eq rq.lock.asString())
        }
        DbAdResponse.success(it)
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse =
        transactionWrapper({
            val res = CarSaleAdTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != CarSaleUserId.NONE) {
                        add(CarSaleAdTable.owner eq rq.ownerId.asString())
                    }
                    if (rq.titleFilter.isNotBlank()) {
                        add(
                            (CarSaleAdTable.carName like "%${rq.titleFilter}%")
                                or (CarSaleAdTable.description like "%${rq.titleFilter}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbAdsResponse(data = res.map { CarSaleAdTable.from(it) }, isSuccess = true)
        }, {
            DbAdsResponse.error(it.asCarSaleError())
        })
}
