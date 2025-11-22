package com.practice.kopring.post.service

import com.practice.kopring.common.response.PageResponse
import com.practice.kopring.post.domain.Post
import com.practice.kopring.post.dto.request.CreatePostRequest
import com.practice.kopring.post.dto.response.PostResponse
import com.practice.kopring.post.repository.jpa.PostRepository
import com.practice.kopring.user.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class PostService (
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) {

    @Transactional
    fun createPost(request: CreatePostRequest,
                   file: List<MultipartFile>?,
                   userSeq: String
    ) {
        val findUser = userRepository.findByUserSeq(userSeq)
        val savedPost = Post(
            postSeq = UUID.randomUUID().toString(),
            imageUrl = request.imageUrl,
            caption = request.caption,
            location = request.location,
            user = findUser
        )

        postRepository.save(savedPost)

    }


    fun getPostList(pageable: Pageable): PageResponse<PostResponse> {

        val result: Page<PostResponse> = postRepository.getPostList(pageable)
        return PageResponse.of(result)
    }

}