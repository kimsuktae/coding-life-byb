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
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22" apply false
    id("com.github.imflog.kafka-schema-registry-gradle-plugin") version "1.11.1"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

group = "com.usktea"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

allprojects {
    group = "com.usktea"
    version = "0.0.1-SNAPSHOT"

    repositories {
        gradlePluginPortal()
        maven("https://packages.confluent.io/maven/")
        maven("https://jitpack.io")
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring") //all-open
    apply(plugin = "com.github.davidmc24.gradle.plugin.avro")
    apply(plugin = "com.github.imflog.kafka-schema-registry-gradle-plugin")

    dependencies {
        // springboot
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // kotlin
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        val avroVersion = "1.11.2"
        implementation("org.apache.avro:avro:$avroVersion")
        implementation("org.apache.avro:avro-compiler:$avroVersion")
        implementation("io.confluent:kafka-avro-serializer:7.3.2")
        implementation("org.springframework.kafka:spring-kafka")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    dependencyManagement {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }

        dependencies {
            dependency("net.logstash.logback:logstash-logback-encoder:6.6")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
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

        download {
            subjectPattern(".*", "$projectDir/src/main/avro")
        }
    }
}

