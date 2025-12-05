package com.practice.kopring.comment.domain

import com.practice.kopring.common.domain.BaseTimeEntity
import com.practice.kopring.post.domain.Post
import com.practice.kopring.user.domain.User
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "comment_seq", unique = true, nullable = false, length = 36)
    val commentSeq: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    @org.hibernate.annotations.Comment("댓글 내용")
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @org.hibernate.annotations.Comment("대댓글인 경우 부모 댓글")
    val parent: Comment? = null,

    @OneToMany(mappedBy = "parent", cascade = [CascadeType.ALL], orphanRemoval = true)
    val replies: MutableList<Comment> = mutableListOf()
) : BaseTimeEntity() {
    fun update(content: String) {
        this.content = content
    }

    fun isReply(): Boolean = parent != null
}