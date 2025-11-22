package com.practice.kopring.auth.service

import com.practice.kopring.auth.dto.CreateUserRequest
import com.practice.kopring.auth.dto.CustomUserDetails
import com.practice.kopring.auth.dto.LoginResponse
import com.practice.kopring.auth.dto.UserLoginRequest
import com.practice.kopring.common.exception.BusinessException
import com.practice.kopring.common.enums.ErrorCode
import com.practice.kopring.jwt.JwtProvider
import com.practice.kopring.user.domain.User
import com.practice.kopring.user.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtProvider: JwtProvider
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
            name = request.name,
            userSeq = UUID.randomUUID().toString(),
        )

        val saveUser = userRepository.save(user)
        if (saveUser.id == null) {
            throw BusinessException(ErrorCode.INTERNAL_SERVER_ERROR)
        }

    }

    fun login(request: UserLoginRequest) : LoginResponse {
        // spring security의 AuthenticationManager를 통한 인증
        val authToken = UsernamePasswordAuthenticationToken(request.username, request.password)
        val authentication: Authentication = authenticationManager.authenticate(authToken)

        // 인증 성공 후 UserDetails에서 사용자 정보 추출
        val userDetails = authentication.principal as CustomUserDetails
        val user = userDetails.getUser()

        // jwt 생성
        val userId: String = user.userSeq
        val accessToken = jwtProvider.generateAccessToken(userId, user.username)
        val refreshToken = jwtProvider.generateRefreshToken(userId, user.username)

        return LoginResponse(accessToken, refreshToken)
    }
}