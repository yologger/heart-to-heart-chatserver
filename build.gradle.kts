import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "com.yologger.h2h"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {

    // Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // DevTools
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Configuration Processor
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Spring Boot Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Spring WebSocket
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    // Spring Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Spring Data Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // Embedded Redis
    implementation("it.ozimov:embedded-redis:0.7.2")

    // Spring Data MongoDB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    // Embedded MongoDB
    implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:3.4.7")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
