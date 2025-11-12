package com.practice.kopring.common.exception

enum class ErrorCode(val status: Int, val code: String, val message: String) {
    // 400 Bad Request 관련
    INVALID_INPUT_VALUE(400, "C001", "잘못된 입력 값입니다."),
    DUPLICATE_RESOURCE(400, "C002", "이미 존재하는 리소스입니다."),

    // 404 Not Found 관련
    NOT_FOUND_RESOURCE(404, "C003", "해당 리소스를 찾을 수 없습니다."),

    // 500 Internal Server Error (서버 오류)
    INTERNAL_SERVER_ERROR(500, "S001", "서버 내부 오류입니다.")

}