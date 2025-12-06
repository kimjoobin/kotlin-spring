package com.practice.kopring.post.service

import com.practice.kopring.common.enums.ErrorCode
import com.practice.kopring.common.exception.BusinessException
import com.practice.kopring.common.response.PageResponse
import com.practice.kopring.common.service.FileStorageService
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
    private val fileService: FileStorageService
) {

    @Transactional
    fun createPost(request: CreatePostRequest,
                   files: List<MultipartFile>,
                   userSeq: String
    ) {
        if (files.isEmpty())
            throw BusinessException(ErrorCode.REQUIRED_IMAGE)

        if (files.size > 10)
            throw BusinessException(ErrorCode.MAX_COUNT_IMAGE)

        val findUser = userRepository.findByUserSeq(userSeq)
            ?: throw BusinessException(ErrorCode.NOT_FOUND_RESOURCE)

        // TODO: 실제 파일업로드 로직 (s3)
        val uploadImageUrl = files.map { file ->
            fileService.storeFile(file)
        }

        val savedPost = Post(
            postSeq = UUID.randomUUID().toString(),
            caption = request.caption,
            location = request.location,
            user = findUser
        )

        savedPost.addImage(uploadImageUrl)

        postRepository.save(savedPost)

        // 유저의 게시글 수 증가
        findUser.increasePostCount()
    }


    fun getPostList(pageable: Pageable): PageResponse<PostResponse> {

        val result: Page<PostResponse> = postRepository.getPostList(pageable)
        return PageResponse.of(result)
    }

    private fun uploadImages(files: List<MultipartFile>): String {
        // 임시로 첫 번째 파일명만 반환 (나중에 S3 업로드로 변경)
        return files.firstOrNull()?.originalFilename ?: "default.jpg"
    }

}