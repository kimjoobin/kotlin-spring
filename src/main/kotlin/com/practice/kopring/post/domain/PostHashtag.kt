package com.practice.kopring.post.domain

import com.practice.kopring.common.domain.BaseTimeEntity
import com.practice.kopring.hashtag.domain.Hashtag
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "post_hashtags",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_post_hashtag",
            columnNames = ["post_id", "hashtag_id"]
        )
    ]
)
class PostHashtag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id", nullable = false)
    val hashtag: Hashtag
) : BaseTimeEntity() {
}