plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":car-sale-api"))
    implementation(project(":car-sale-common"))

    testImplementation(kotlin("test-junit"))
}