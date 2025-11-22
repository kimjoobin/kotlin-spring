package com.practice.kopring.post.dto.response

import com.practice.kopring.user.dto.response.AuthorInfo
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class PostResponse @QueryProjection constructor(
    val postSeq: String,  // PK 대신 postSeq 노출
    val imageUrl: String,
    val caption: String?,
    val location: String?,
    val likeCount: Int,
    val commentCount: Int,
    val author: AuthorInfo,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
