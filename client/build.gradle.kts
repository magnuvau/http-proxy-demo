val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val prometeusVersion : String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
}

group = "github.magnuvau.com.client"
version = "0.0.1"
application {
    mainClass.set("github.magnuvau.com.client.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}
