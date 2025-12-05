package com.practice.kopring.auth.controller

import com.practice.kopring.auth.dto.CreateUserRequest
import com.practice.kopring.auth.dto.LoginResponse
import com.practice.kopring.auth.dto.UserLoginRequest
import com.practice.kopring.auth.service.AuthService
import com.practice.kopring.common.response.ApiResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@Tag(name = "AuthController", description = "인증 컨트롤러")
class AuthController (
    private val authService: AuthService
) {

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    fun signup(@RequestBody @Valid request: CreateUserRequest): ResponseEntity<ApiResponse<Nothing>> {
        authService.signup(request)
        return ResponseEntity.ok(
            ApiResponse.success("회원가입이 완료되었습니다.", HttpStatus.CREATED.value())
        )
    }

    private val log = KotlinLogging.logger {}  // 괄호 필수!

    @PostMapping("/login")
    @Operation(summary = "로그인")
    fun login(@RequestBody @Valid request: UserLoginRequest): ResponseEntity<ApiResponse<LoginResponse>> {
        val response = authService.login(request)
        log.info { "response: $response" }
        return ResponseEntity.ok(
            ApiResponse.success(response, "로그인에 성공하였습니다.", HttpStatus.OK.value())
        )
    }

}