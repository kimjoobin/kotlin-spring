package com.practice.kopring.auth.controller

import com.practice.kopring.auth.dto.CreateUserRequest
import com.practice.kopring.auth.service.AuthService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/auth")
class AuthController (
    private val authService: AuthService
) {


    @PostMapping("/signup")
    fun signup(@RequestBody request: CreateUserRequest): String {
        return "auth/signup"
    }

}