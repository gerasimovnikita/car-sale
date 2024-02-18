package com.github.gerasimovnikita.otus.carsale.repo.sql

import models.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
object CarSaleAdTable : Table("ad") {
    val id = varchar("id", 128)
    val carName = varchar("car_name", 128)
    val description = varchar("description", 512)
    val owner = varchar("owner", 128)
    val visibility = enumeration("visibility", CarSaleAdVisibility::class)
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = CarSaleAd(
        id = CarSaleAdId(res[id].toString()),
        carName = res[carName],
        description = res[description],
        ownerId = CarSaleUserId(res[owner].toString()),
        visibility = res[visibility],
        lock = CarSaleAdLock(res[lock])
    )

    fun from(res: ResultRow) = CarSaleAd(
        id = CarSaleAdId(res[id].toString()),
        carName = res[carName],
        description = res[description],
        ownerId = CarSaleUserId(res[owner].toString()),
        visibility = res[visibility],
        lock = CarSaleAdLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, ad: CarSaleAd, randomUuid: () -> String) {
        it[id] = ad.id.takeIf { it != CarSaleAdId.NONE }?.asString() ?: randomUuid()
        it[carName] = ad.carName
        it[description] = ad.description
        it[owner] = ad.ownerId.asString()
        it[visibility] = ad.visibility
        it[lock] = ad.lock.takeIf { it != CarSaleAdLock.NONE }?.asString() ?: randomUuid()
    }

}
