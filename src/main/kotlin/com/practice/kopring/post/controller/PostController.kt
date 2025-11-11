package com.practice.kopring.post.controller

import com.practice.kopring.post.service.PostService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping("/posts")
class PostController (
    private val postService: PostService
) {

    @GetMapping("/feed")
    fun feed(
        @RequestParam(defaultValue = "0") page: Int,
        model: Model
    ): String {
        val posts = postService.getFeed(page)
        model.addAttribute("posts", posts)
        return "posts/feed"
    }

    @GetMapping("/new")
    fun createForm(): String {
        return "posts/create"
    }

    @PostMapping("")
    fun createBoard(
        @RequestParam title: String,
        @RequestParam content: String,
        @RequestParam(required = false) file: MultipartFile?,
    ): String {
        postService.createPost(title, content, file)
        return "redirect:/posts/feed"
    }

}