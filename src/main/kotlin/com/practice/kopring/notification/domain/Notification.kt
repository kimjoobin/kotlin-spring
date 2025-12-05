package com.practice.kopring.notification.domain

import com.practice.kopring.common.domain.BaseTimeEntity
import com.practice.kopring.post.domain.Post
import com.practice.kopring.user.domain.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.Comment

@Entity
class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "notification_seq", unique = true, nullable = false, length = 36)
    val notificationSeq: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    @Comment("알림 받는 사람")
    val receiver: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    @Comment("알림 보낸 사람")
    val sender: User,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Comment("알림 타입")
    val type: NotificationType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    val comment: com.practice.kopring.comment.domain.Comment? = null,

    @Column(nullable = false)
    @Comment("읽음 여부")
    var isRead: Boolean = false

) : BaseTimeEntity() {
    fun markAsRead() {
        this.isRead = true
    }
}

enum class NotificationType {
    FOLLOW,   // 팔로우
    LIKE,     // 좋아요
    COMMENT   // 댓글
}