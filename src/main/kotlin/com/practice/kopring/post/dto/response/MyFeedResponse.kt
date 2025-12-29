package com.practice.kopring.post.dto.response

import com.querydsl.core.annotations.QueryProjection

data class MyFeedResponse @QueryProjection constructor(
    val postSeq: String,  // PK 대신 postSeq 노출
    val likeCount: Int,
    val commentCount: Int,
    val thumbnail: String
)
