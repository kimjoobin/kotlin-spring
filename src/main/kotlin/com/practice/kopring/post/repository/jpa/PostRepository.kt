package com.practice.kopring.post.repository.jpa

import com.practice.kopring.post.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
}