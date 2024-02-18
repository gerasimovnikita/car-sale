plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm { }

    sourceSets {
        val coroutinesVersion: String by project
        val datetimeVersion: String by project

        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":car-sale-api-log"))
                implementation(project(":car-sale-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(project(":car-sale-api-log"))
                implementation(project(":car-sale-common"))

                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }

    }
}
