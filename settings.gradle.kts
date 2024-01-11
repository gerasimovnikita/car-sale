rootProject.name = "car-sale"
include("m1l1-hello")
include("car-sale-acceptance")
include("car-sale-api")
include("car-sale-api-log")
include("car-sale-common")
include("car-sale-mappers")
include("car-sale-mappers-log")
include("car-sale-biz")
include("car-sale-stubs")
include("car-sale-app-ktor")
include("car-sale-app-common")
include("car-sale-repo-stubs")
include("car-sale-repo-tests")
include("car-sale-repo-in-memory")
include("car-sale-repo-postgresql")
include("car-sale-lib-cor")
include("car-sale-lib-logging-common")
include("car-sale-lib-logging-kermit")
include("car-sale-lib-logging-logback")

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
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
