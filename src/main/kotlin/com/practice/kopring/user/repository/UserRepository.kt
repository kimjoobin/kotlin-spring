package com.practice.kopring.user.repository

import com.practice.kopring.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

    fun findByEmail(email: String): User?

    fun findByUserSeq(userSeq: String): User?

    fun existsByEmail(email: String): Boolean

    fun existsByUsername(username: String): Boolean
}