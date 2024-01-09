plugins {
    kotlin("multiplatform")
}

group = rootProject.group
version = rootProject.version

kotlin {
    jvm {}

    sourceSets {
        val kermitLoggerVersion: String by project
        val coroutinesVersion: String by project
        val datetimeVersion: String by project
        val serializationVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":car-sale-lib-logging-common"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("co.touchlab:kermit:$kermitLoggerVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }

    }
    jvmToolchain(8)
}
//dependencies {
//    implementation(kotlin("stdlib-jdk8"))
//}
//repositories {
//    mavenCentral()
//}