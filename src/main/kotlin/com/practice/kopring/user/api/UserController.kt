package com.practice.kopring.user.api

import com.practice.kopring.auth.dto.CustomUserDetails
import com.practice.kopring.common.response.ApiResponse
import com.practice.kopring.user.dto.response.MyInfoResponse
import com.practice.kopring.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
@Tag(name = "UserController", description = "회원 컨트롤러")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/me")
    @Operation(summary = "내 정보 조회", description = "내 정보 조회")
    fun getMyInfo(
        @AuthenticationPrincipal user: CustomUserDetails
    ): ResponseEntity<ApiResponse<MyInfoResponse>> {

        return ResponseEntity.ok(
            ApiResponse.success(
                userService.getMyInfo(user),
                "조회 성공",
                HttpStatus.OK.value()
            )
        )
    }
}