package com.practice.kopring.post.dto.request

data class CreatePostRequest(
    val imageUrl: String,
    val caption: String? = null,
    val location: String? = null,
)
