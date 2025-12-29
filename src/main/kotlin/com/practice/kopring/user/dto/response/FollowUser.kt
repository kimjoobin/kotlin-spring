package com.practice.kopring.user.dto.response

data class FollowUser(
    val userSeq: String,
    val username: String,
    val name: String,
    val profileImage: String,
    val isFollowing: Boolean
)
