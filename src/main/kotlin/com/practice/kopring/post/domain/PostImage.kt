package com.practice.kopring.post.domain

import com.practice.kopring.common.domain.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.Comment

@Entity
class PostImage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "image_seq", unique = true, nullable = false, length = 36)
    val imageSeq: String,

    @Column(name = "path", columnDefinition = "VARCHAR(300)")
    @Comment("이미지 경로")
    val path: String,

    @Column(name = "image_order", nullable = false)
    @Comment("이미지 순서")
    val imageOrder: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, foreignKey = ForeignKey(name = "fk_post_image_post_id"))
    val post: Post

) : BaseTimeEntity() {
}