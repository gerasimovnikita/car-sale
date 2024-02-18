plugins {
    kotlin("multiplatform")
}



kotlin {
    jvm {}

    sourceSets {
        val coroutinesVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(project(":car-sale-common"))
                implementation(project(":car-sale-stubs"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(project(":car-sale-repo-tests"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
