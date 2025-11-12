package com.practice.kopring.auth.service

import com.practice.kopring.auth.dto.CreateUserRequest
import com.practice.kopring.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val userRepository: UserRepository,
) {

    fun signup(request: CreateUserRequest): String {
        // 중복검사
        return ""
    }
}