rootProject.name = "car-sale"
include("m1l1-hello")
include("car-sale-acceptance")

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
    }
}