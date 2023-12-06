rootProject.name = "car-sale"
include("m1l1-hello")
include("car-sale-acceptance")
include("car-sale-api")
include("car-sale-common")
include("car-sale-mappers")
include("car-sale-biz")
include("car-sale-stubs")
include("car-sale-app-ktor")

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val pluginJpa: String by settings
    val ktorVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false

        kotlin("plugin.serialization") version kotlinVersion apply false
        kotlin("plugin.jpa") version pluginJpa apply false

        id("org.openapi.generator") version openapiVersion apply false
        id("io.ktor.plugin") version ktorVersion apply false
    }
}