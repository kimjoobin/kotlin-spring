package com.practice.kopring.common.enums

enum class ErrorCode(val status: Int, val message: String) {
    // 400 Bad Request 관련
    INVALID_INPUT_VALUE(400, "잘못된 입력 값입니다."),

    // 401 - Unauthorized
    LOGIN_FAILED(401, "아이디 또는 비밀번호를 잘못 입력하였습니다. 다시 시도해주세요."),
    UNAUTHORIZED(401, "로그인이 필요한 서비스입니다. 로그인 후 다시 이용해주세요."),

    // 403 - Forbidden
    EXPIRED_TOKEN(403, "토큰이 만료되었습니다."),
    INVALID_TOKEN(403, "유효하지 않은 토큰입니다."),
    FORBIDDEN_USER(403, "접근 권한이 없습니다. 다시 시도해주세요"),

    // 404 Not Found 관련
    NOT_FOUND_RESOURCE(404, "해당 리소스를 찾을 수 없습니다."),

    // 409 conflict
    DUPLICATE_EMAIL(409, "이미 존재하는 이메일입니다."),
    DUPLICATE_USERNAME(409, "이미 존재하는 사용자명입니다."),

    // 500 Internal Server Error (서버 오류)
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류입니다.")


}