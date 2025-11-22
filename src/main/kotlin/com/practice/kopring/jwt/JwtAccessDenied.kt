package com.practice.kopring.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.practice.kopring.common.enums.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class JwtAccessDenied : AccessDeniedHandler {

    // 💡 접근 권한이 없는 리소스에 접근 시 호출
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        // HTTP 상태 코드를 403 Forbidden으로 설정
        response.status = HttpServletResponse.SC_FORBIDDEN

        // Content Type을 JSON으로 설정
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"

        // 커스텀 에러 응답 본문 생성
        val errorResponse = mapOf(
            "status" to ErrorCode.FORBIDDEN_USER.status,
            "message" to ErrorCode.FORBIDDEN_USER.message,
            "error" to ErrorCode.FORBIDDEN_USER
        )

        // JSON 응답 작성
        ObjectMapper().writeValue(response.writer, errorResponse)
    }
}