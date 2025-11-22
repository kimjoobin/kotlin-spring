package com.practice.kopring.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.practice.kopring.common.enums.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        // HTTP 상태 코드를 401 Unauthorized로 설정
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        // Content Type을 JSON으로 설정
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"

        // 커스텀 에러 응답 본문 생성
        val errorResponse = mapOf(
            "status" to ErrorCode.UNAUTHORIZED.status,
            "message" to ErrorCode.UNAUTHORIZED.message,
            "error" to ErrorCode.UNAUTHORIZED
        )

        // JSON 응답 작성
        ObjectMapper().writeValue(response.writer, errorResponse)
    }
}