plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {}

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":car-sale-common"))
            }
        }
//        val commonTest by getting {
//            dependencies {
//                implementation(kotlin("test-common"))
//                implementation(kotlin("test-annotations-common"))
//            }
//        }
//        val jvmMain by getting {
//            dependencies {
//                implementation(kotlin("stdlib"))
//            }
//        }
//        val jvmTest by getting {
//            dependencies {
//                implementation(kotlin("test-junit"))
//            }
//        }
    }
}
