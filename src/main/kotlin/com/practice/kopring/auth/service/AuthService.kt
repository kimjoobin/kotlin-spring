package com.practice.kopring.auth.service

import com.practice.kopring.auth.dto.CreateUserRequest
import com.practice.kopring.auth.dto.LoginResponse
import com.practice.kopring.auth.dto.UserLoginRequest
import com.practice.kopring.common.exception.BusinessException
import com.practice.kopring.common.enums.ErrorCode
import com.practice.kopring.user.domain.User
import com.practice.kopring.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun signup(request: CreateUserRequest) {
        // 중복검사
        if (userRepository.existsByEmail(request.email)) {
            throw BusinessException(ErrorCode.DUPLICATE_EMAIL)
        }

        if (userRepository.existsByUsername(request.username)) {
            throw BusinessException(ErrorCode.DUPLICATE_USERNAME)
        }

        val encodePassword = passwordEncoder.encode(request.password)

        val user = User(
            username = request.username,
            email = request.email,
            password = encodePassword,
            name = request.name
        )

        val saveUser = userRepository.save(user)
        if (saveUser.id == null) {
            throw BusinessException(ErrorCode.INTERNAL_SERVER_ERROR)
        }

    }

    fun login(request: UserLoginRequest) : LoginResponse {
        return LoginResponse("", "")
    }
}