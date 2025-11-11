package com.practice.kopring.post.service

import com.practice.kopring.post.repository.jpa.PostRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PostService (
    private val postRepository: PostRepository,
) {
    fun createPost(title: String, content: String, file: MultipartFile?): String {
        TODO("Not yet implemented")
    }

    fun getFeed(page: Int) {

    }
}