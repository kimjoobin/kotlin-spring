package com.practice.kopring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class KotlinSpringApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringApplication>(*args)
}
