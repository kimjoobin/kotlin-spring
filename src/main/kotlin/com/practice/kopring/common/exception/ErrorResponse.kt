package com.practice.kopring.common.exception

data class ErrorResponse(
    // HTTP 상태 코드 (예: 400, 404, 500)
    val status: Int,
    // 클라이언트에게 보여줄 오류 메시지
    val message: String,
)
