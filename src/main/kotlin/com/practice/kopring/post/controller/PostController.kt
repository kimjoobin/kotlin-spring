package com.practice.kopring.post.controller

import com.practice.kopring.post.service.PostService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/post")
class PostController (
    private val postService: PostService
) {

}