package com.practice.kopring.common

data class ApiResponse<T: Any?>(
    // 요청 처리 성공 여부
    val success: Boolean,

    // HTTP 상태 코드 (예: 200, 201)
    val status: Int,

    // 클라이언트에게 전달할 메시지
    val message: String,

    // 실제 응답 데이터 (Nullable)
    val data: T? = null
) {
    companion object {
        // 1. 성공 응답을 위한 팩토리 함수 (데이터 포함)
        fun <T : Any> success(data: T, message: String = "요청 성공", status: Int = 200): ApiResponse<T> {
            return ApiResponse(
                success = true,
                status = status,
                message = message,
                data = data
            )
        }

        // 2. 성공 응답을 위한 팩토리 함수 (데이터 없음 - 예: 삭제, 업데이트 성공)
        fun success(message: String = "요청 성공", status: Int = 200): ApiResponse<Nothing> {
            return ApiResponse(
                success = true,
                status = status,
                message = message,
                data = null
            )
        }

        // 5. 실패 응답을 위한 팩토리 함수
        fun <T : Any> error(message: String, status: Int = 400, data: T? = null): ApiResponse<T> {
            return ApiResponse(
                success = false,
                status = status,
                message = message,
                data = data
            )
        }
    }
}
