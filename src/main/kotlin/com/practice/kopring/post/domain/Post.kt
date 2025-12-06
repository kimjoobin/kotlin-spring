package com.practice.kopring.post.domain

import com.practice.kopring.common.domain.BaseTimeEntity
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
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate
import java.util.UUID

@Entity
@DynamicUpdate
class Post (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "post_seq", unique = true, nullable = false, length = 36)
    val postSeq: String,

    @Column(columnDefinition = "LONGTEXT")
    @Comment("본문")
    var caption: String? = null,

    @Column(length = 500)
    @Comment("이미지 URL, 일단 1개만")
    var imageUrl: String? = null,

    @Column(nullable = false)
    @Comment("좋아요 수")
    var likeCount: Int = 0,
    
    @Column(nullable = false)
    @Comment("댓글 수")
    var commentCount: Int = 0,
    
    @Column(length = 100)
    @Comment("위치")
    var location: String? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    val images: MutableList<PostImage> = mutableListOf()
) : BaseTimeEntity() {

    fun update(caption: String?, location: String?) {
        this.caption = caption
        this.location = location
    }

    fun addImage(imagePath: List<String>) {
        imagePath.forEachIndexed { index, path ->
            val postImage = PostImage(
                imageSeq = UUID.randomUUID().toString(),
                path = path,
                imageOrder = index,
                post = this
            )
            images.add(postImage)
        }
    }

    fun increaseLikeCount() {
        this.likeCount++
    }

    fun decreaseLikeCount() {
        if (this.likeCount > 0) this.likeCount--
    }

    fun increaseCommentCount() {
        this.commentCount++
    }

    fun decreaseCommentCount() {
        if (this.commentCount > 0) this.commentCount--
    }
}
