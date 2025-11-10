plugins {
    kotlin("jvm") version "1.9.25"  // kotlin을 JVM에서 실행하기 위한 기본 플러그인
    kotlin("plugin.spring") version "1.9.25"    // Kotlin과 스프링 프레임워크를 함께 사용하기 위한 플러그인
    kotlin("plugin.jpa") version "1.9.25"   // kotiln 언어ㅘ JPA를 함께 사용할 때 발생하는 문제를 해결하기 위한 플러그인
    id("org.springframework.boot") version "3.4.11"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.practice"
version = "0.0.1-SNAPSHOT"
description = "kotlin_spring"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen { // ← JPA Entity를 open class로 만들어줌
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
