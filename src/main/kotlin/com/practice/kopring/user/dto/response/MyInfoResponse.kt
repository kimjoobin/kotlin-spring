package com.practice.kopring.user.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class MyInfoResponse(
    @Schema(description = "유저 시퀀스")
    val userSeq: String,

    @Schema(description = "로그인 ID")
    val username: String,

    @Schema(description = "회원명")
    val name: String,

    @Schema(description = "이메일")
    val email: String,

    @Schema(description = "팔로잉 수")
    val followingCount: Int,

    @Schema(description = "팔로워 수")
    val followersCount: Int,

    @Schema(description = "게시글 수")
    val postsCount: Int,

    @Schema(description = "프로필 이미지")
    val profileImageUrl: String?,

    @Schema(description = "소개")
    val bio: String?,
)
