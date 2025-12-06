plugins {
    kotlin("jvm") version "1.9.25"  // kotlin을 JVM에서 실행하기 위한 기본 플러그인
    kotlin("plugin.spring") version "1.9.25"    // Kotlin과 스프링 프레임워크를 함께 사용하기 위한 플러그인
    kotlin("plugin.jpa") version "1.9.25"   // kotiln 언어ㅘ JPA를 함께 사용할 때 발생하는 문제를 해결하기 위한 플러그인
    id("org.springframework.boot") version "3.4.11"
    id("io.spring.dependency-management") version "1.1.7"

    kotlin("kapt") version "1.9.25"  // QueryDSL을 위한 kapt 추가
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
    // spring boot
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework.boot:spring-boot-starter-validation")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Swagger/OpenAPI 3
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")

    //security
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    //jwt
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // 작성하는 시점의 최신 버전이 5.1.1 이어서 사용함
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.1")

    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    // p6spy
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }

    sourceSets.main {
        kotlin.srcDir("$buildDir/generated/source/kapt/main")
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

// kapt 설정
kapt {
    arguments {
        arg("plugin", "com.querydsl.apt.jpa.JPAAnnotationProcessor")
    }
}