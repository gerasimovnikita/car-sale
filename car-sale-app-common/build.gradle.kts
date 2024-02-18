plugins {
    kotlin("multiplatform")
}

group = rootProject.group
version = rootProject.version

kotlin {
    jvm {}

    sourceSets {
        val coroutinesVersion: String by project
        val datetimeVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
                implementation(project(":car-sale-lib-logging-common"))
                implementation(project(":car-sale-biz"))
                implementation(project(":car-sale-common"))
                implementation(project(":car-sale-mappers-log"))
                implementation(project(":car-sale-api-log"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(project(":car-sale-biz"))
                implementation(project(":car-sale-common"))
                implementation(project(":car-sale-mappers-log"))
                implementation(project(":car-sale-api-log"))
            }
        }
    }
}
