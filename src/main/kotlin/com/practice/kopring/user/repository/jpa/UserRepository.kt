package com.practice.kopring.user.repository.jpa

import com.practice.kopring.common.response.PageResponse
import com.practice.kopring.user.domain.User
import com.practice.kopring.user.dto.response.FollowUser
import com.practice.kopring.user.repository.query.UserRepositoryCustom
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>, UserRepositoryCustom {
    fun findByUsername(username: String): User?

    fun findByEmail(email: String): User?

    fun findByUserSeq(userSeq: String): User?

    fun existsByEmail(email: String): Boolean

    fun existsByUsername(username: String): Boolean
}