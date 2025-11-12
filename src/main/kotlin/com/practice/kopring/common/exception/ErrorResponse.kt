package com.practice.kopring.common.exception

data class ErrorResponse(
    // HTTP 상태 코드 (예: 400, 404, 500)
    val status: Int,
    // 클라이언트에게 보여줄 오류 메시지
    val message: String,
    // 내부적으로 오류를 식별하기 위한 코드 (선택 사항)
    val code: String? = null,
)
