package com.practice.kopring.post.repository.jpa

import com.practice.kopring.post.domain.Post
import com.practice.kopring.post.repository.query.PostRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PostRepository : JpaRepository<Post, Long>, PostRepositoryCustom {

    fun findByPostSeq(postSeq: String) : Optional<Post>
}