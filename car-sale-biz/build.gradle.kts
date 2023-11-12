plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {}

    sourceSets {

        all { languageSettings.optIn("kotlin.RequiresOptIn") }
        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(project(":car-sale-common"))
                implementation(project(":car-sale-stubs"))
            }
        }
//        @Suppress("UNUSED_VARIABLE")
//        val commonTest by getting {
//            dependencies {
//                implementation(kotlin("test-junit"))
//            }
//        }
    }
}
