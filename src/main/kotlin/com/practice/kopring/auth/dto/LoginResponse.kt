package com.practice.kopring.auth.dto

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
)
