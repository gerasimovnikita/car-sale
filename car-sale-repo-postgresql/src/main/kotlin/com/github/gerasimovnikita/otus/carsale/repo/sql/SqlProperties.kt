package com.github.gerasimovnikita.otus.carsale.repo.sql

open class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5434/car-sale-db",
    val user: String = "postgres",
    val password: String = "password",
    val schema: String = "car-sale",
    // Delete tables at startup - needed for testing
    val dropDatabase: Boolean = false,
)
