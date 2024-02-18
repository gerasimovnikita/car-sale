plugins {
    kotlin("multiplatform")
}



kotlin {
    jvm {}

    sourceSets {
        val coroutinesVersion: String by project

        val commonMain by getting {
            dependencies {
                implementation(project(":car-sale-common"))

                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
                api(kotlin("test-junit"))
            }
        }
    }
}
