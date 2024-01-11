import org.jetbrains.kotlin.util.suffixIfNot

plugins {
    application
    kotlin("jvm")
    id("io.ktor.plugin")
}

val ktorVersion: String by project
val logbackVersion: String by project

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }
}

fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

application{
    mainClass.set("com.github.gerasimovnikita.otus.carsale.app.ktor.ApplicationKt")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
//    implementation(ktor("cio")) // "io.ktor:ktor-ktor-server-cio:$ktorVersion"
    implementation(ktor("netty")) // "io.ktor:ktor-ktor-server-cio:$ktorVersion"
    implementation(ktor("config-yaml"))

    // jackson
    implementation(ktor("jackson", "serialization")) // io.ktor:ktor-serialization-jackson
    implementation(ktor("content-negotiation")) // io.ktor:ktor-server-content-negotiation
    implementation(ktor("kotlinx-json", "serialization")) // io.ktor:ktor-serialization-kotlinx-json

    implementation(ktor("locations"))
    implementation(ktor("caching-headers"))
    implementation(ktor("call-logging"))
    implementation(ktor("auto-head-response"))
    implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
    implementation(ktor("default-headers")) // "io.ktor:ktor-cors:$ktorVersion"
    implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
    implementation(ktor("auto-head-response"))

    implementation("io.ktor:ktor-server-websockets-jvm:2.2.4")
    implementation(ktor("websockets")) // "io.ktor:ktor-websockets:$ktorVersion"
    implementation(ktor("auth")) // "io.ktor:ktor-auth:$ktorVersion"
    implementation(ktor("auth-jwt")) // "io.ktor:ktor-auth-jwt:$ktorVersion"

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // transport models
    implementation(project(":car-sale-api"))
    implementation(project(":car-sale-mappers"))
    implementation(project(":car-sale-common"))
    implementation(project(":car-sale-biz"))
    implementation(project(":car-sale-stubs"))
    implementation(project(":car-sale-app-common"))

    implementation(project(":car-sale-repo-in-memory"))
    implementation(project(":car-sale-repo-postgresql"))
    implementation(project(":car-sale-lib-logging-common"))
    implementation(project(":car-sale-lib-logging-kermit"))
    implementation(project(":car-sale-lib-logging-logback"))
    implementation(project(":car-sale-repo-stubs"))

    testImplementation(kotlin("test-junit"))
    implementation(ktor("test-host"))

    testImplementation(ktor("websockets"))
    testImplementation(ktor("content-negotiation", prefix = "client-")) // io.ktor:ktor-server-content-negotiation
    testImplementation(project(":car-sale-repo-in-memory"))
    testImplementation(project(":car-sale-repo-stubs"))
}
