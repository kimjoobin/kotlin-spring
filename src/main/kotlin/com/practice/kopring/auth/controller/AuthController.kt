package com.practice.kopring.auth.controller

import com.practice.kopring.auth.service.AuthService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/auth")
class AuthController (
    private val authService: AuthService
) {

    @GetMapping("/login")
    fun loginPage(model: Model): String {
        return "auth/login"
    }

    @GetMapping("/signup")
    fun signupPage(model: Model): String {
        return "auth/signup"
    }

    @PostMapping("/login")
    fun loginAction(model: Model): String {
        return "redirect:/posts/feed"
    }
}