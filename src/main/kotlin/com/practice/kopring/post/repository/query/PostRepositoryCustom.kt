package com.practice.kopring.post.repository.query

import com.practice.kopring.post.dto.response.PostResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostRepositoryCustom {
    fun getPostList(pageable: Pageable): Page<PostResponse>

    fun getMyFeed(pageable: Pageable, userSeq: String): Page<PostResponse>
}