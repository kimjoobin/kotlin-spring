package com.practice.kopring.user.dto.response

import com.querydsl.core.annotations.QueryProjection

data class AuthorInfo @QueryProjection constructor(
    val userSeq: String,
    val username: String,
    val email: String,
    val profileImageUrl: String?
)
