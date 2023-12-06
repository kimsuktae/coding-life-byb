import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

buildscript {
    repositories {
        gradlePluginPortal()
        maven("https://packages.confluent.io/maven/")
        maven("https://jitpack.io")
    }
}

plugins {
    id("org.springframework.boot") version "3.0.12"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    id("com.github.imflog.kafka-schema-registry-gradle-plugin") version "1.11.1"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
        maven("https://packages.confluent.io/maven/")
    }
}

group = "com.kafka-schema"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    val avroVersion = "1.11.2"
    implementation("org.apache.avro:avro:$avroVersion")
    implementation("org.apache.avro:avro-compiler:$avroVersion")
    implementation("io.confluent:kafka-avro-serializer:7.3.2")
    implementation("org.springframework.kafka:spring-kafka")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}

schemaRegistry {
    val schemaRegistryEndpoint = System.getenv("SCHEMA_REGISTRY_ENDPOINT")
        ?: project.properties["schemaRegistryEndpoint"]?.toString()
        ?: "http://43.201.148.192:8081"

    val schemaRegistryUsername = System.getenv("SCHEMA_REGISTRY_USERNAME")
        ?: project.properties["schemaRegistryUsername"]?.toString()
        ?: "user1"

    val schemaRegistryPassword = System.getenv("SCHEMA_REGISTRY_PASSWORD")
        ?: project.properties["schemaRegistryPassword"]?.toString()
        ?: "password1"

    url.set(schemaRegistryEndpoint)
    credentials {
        username.set(schemaRegistryUsername)
        password.set(schemaRegistryPassword)
    }

    println(projectDir)

    download {
        subjectPattern(".*", "$projectDir/src/main/avro")
    }
}
