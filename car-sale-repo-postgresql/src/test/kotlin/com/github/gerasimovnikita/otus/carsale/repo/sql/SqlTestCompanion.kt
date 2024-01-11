package com.github.gerasimovnikita.otus.carsale.repo.sql

import com.benasher44.uuid.uuid4
import models.CarSaleAd
import org.testcontainers.containers.PostgreSQLContainer
import java.time.Duration

class PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres:16.1")

object SqlTestCompanion {
    private const val USER = "postgres"
    private const val PASS = "password"
    private const val SCHEMA = "car-sale"

    private val container by lazy {
        PostgresContainer().apply {
            withUsername(USER)
            withPassword(PASS)
            withDatabaseName(SCHEMA)
            withStartupTimeout(Duration.ofSeconds(300L))
            start()
        }
    }

    private val url: String by lazy { container.jdbcUrl }

    fun repoUnderTestContainer(
        initObjects: Collection<CarSaleAd> = emptyList(),
        randomUuid: () -> String = { uuid4().toString() },
    ): RepoAdSQL {
        return RepoAdSQL(
            SqlProperties(url, USER, PASS, SCHEMA, dropDatabase = true),
            initObjects, randomUuid = randomUuid)
    }
}
