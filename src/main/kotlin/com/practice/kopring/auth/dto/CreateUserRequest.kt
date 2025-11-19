package com.practice.kopring.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank(message = "사용자명은 필수입니다.")
    @Schema(defaultValue = "사용자명(로그인 ID)")
    val username: String,
    
    @field:NotBlank(message = "이메일은 필수입니다.")
    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    @Schema(defaultValue = "이메일")
    val email: String,
    
    @field:NotBlank(message = "비밀번호는 필수입니다.")
    @Schema(defaultValue = "비밀번호")
    val password: String,

    @field:NotBlank(message = "이름은 필수입니다.")
    @Schema(defaultValue = "이름")
    val name: String
)
