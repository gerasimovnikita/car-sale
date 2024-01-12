plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {}

    sourceSets {
        val coroutinesVersion: String by project

        all { languageSettings.optIn("kotlin.RequiresOptIn") }
        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(project(":car-sale-common"))
                implementation(project(":car-sale-stubs"))
                implementation(project(":car-sale-lib-cor"))
                implementation(project(":car-sale-lib-logging-common"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(kotlin("test-annotations-common"))
                implementation(project(":car-sale-repo-stubs"))
                implementation(project(":car-sale-repo-tests"))

                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }
    }
}
