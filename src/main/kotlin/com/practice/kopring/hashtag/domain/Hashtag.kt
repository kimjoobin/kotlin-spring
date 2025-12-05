package com.practice.kopring.hashtag.domain

import com.practice.kopring.common.domain.BaseTimeEntity
import com.practice.kopring.post.domain.PostHashtag
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.hibernate.annotations.Comment

@Entity
class Hashtag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "hashtag_seq", unique = true, nullable = false, length = 36)
    val hashtagSeq: String,

    @Column(unique = true, nullable = false, length = 50)
    @Comment("해시태그 이름")
    val name: String,

    @Column(nullable = false)
    @Comment("사용된 게시글 수")
    var postCount: Int = 0,

    @OneToMany(mappedBy = "hashtag", cascade = [CascadeType.ALL], orphanRemoval = true)
    val postHashtags: MutableList<PostHashtag> = mutableListOf()
) : BaseTimeEntity() {
    fun increasePostCount() {
        this.postCount++
    }

    fun decreasePostCount() {
        if (this.postCount > 0) this.postCount--
    }
}