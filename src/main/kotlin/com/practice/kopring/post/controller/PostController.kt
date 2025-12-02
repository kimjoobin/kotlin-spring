package com.practice.kopring.post.controller

import com.practice.kopring.auth.dto.CustomUserDetails
import com.practice.kopring.common.response.ApiResponse
import com.practice.kopring.common.response.PageResponse
import com.practice.kopring.post.dto.request.CreatePostRequest
import com.practice.kopring.post.dto.response.PostResponse
import com.practice.kopring.post.service.PostService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/posts")
@Tag(name = "PostController", description = "피드 컨트롤러")
class PostController (
    private val postService: PostService
) {

    @PostMapping("", consumes = ["multipart/form-data"])
    @Operation(summary = "피드 등록", description = "피드를 등록합니다.")
    fun createPost(
        @RequestPart(value = "request") request: CreatePostRequest,
        @RequestPart(value = "files", required = false) file: List<MultipartFile>,
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): ResponseEntity<ApiResponse<Nothing>> {
        val userId = userDetails.getUserSeq()

        postService.createPost(request, file, userId)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.success("등록되었습니다.", HttpStatus.CREATED.value())
        )
    }

    @GetMapping("")
    fun getPostList(
        pageable: Pageable
    ): ResponseEntity<ApiResponse<PageResponse<PostResponse>>> {
        return ResponseEntity.ok(
            ApiResponse.success(postService.getPostList(pageable), "조회 성공", HttpStatus.OK.value())
        )
    }
}