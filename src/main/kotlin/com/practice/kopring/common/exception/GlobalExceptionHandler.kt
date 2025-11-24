package com.practice.kopring.common.exception

import com.practice.kopring.common.enums.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException

@RestControllerAdvice
class GlobalExceptionHandler {

    // 1. **비즈니스 커스텀 예외 처리**
    // (예: ResourceNotFoundException 등 BusinessException을 상속받은 모든 예외)
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponse> {
        val errorCode = e.errorCode
        val response = ErrorResponse(
            status = errorCode.status,
            message = errorCode.message
        )
        // HTTP 상태 코드는 Enum에 정의된 status 값을 따릅니다.
        return ResponseEntity.status(errorCode.status).body(response)
    }

    // 2. **스프링 기본 Validation 예외 처리**
    // (예: @Valid 사용 시 DTO 필드 검증 실패)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        // 첫 번째 에러 메시지를 가져와서 응답에 포함
        val errorMessage = e.bindingResult.fieldErrors.firstOrNull()?.defaultMessage
            ?: ErrorCode.INVALID_INPUT_VALUE.message

        val response = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = errorMessage // 상세 메시지 사용
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    // 3. **최상위 예외 처리 (모든 처리되지 않은 예외)**
    // (예: 예상치 못한 NullPointerException 등)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        // 서버 로그에는 자세한 스택 트레이스를 기록합니다. (Logger 사용 권장)
        // log.error("Unhandled Exception: ", e)

        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR
        val response = ErrorResponse(
            status = errorCode.status,
            message = errorCode.message
        )
        // 500 Internal Server Error 반환
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(e: BadCredentialsException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.LOGIN_FAILED
        val response = ErrorResponse(
            status = errorCode.status,
            message = errorCode.message
        )

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
    }

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxupSizeExceededException(e: MaxUploadSizeExceededException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ErrorCode.MAX_SIZE_FILE.message
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(response)
    }
}