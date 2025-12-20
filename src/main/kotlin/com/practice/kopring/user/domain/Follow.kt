package com.practice.kopring.user.domain

import com.practice.kopring.common.domain.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.Comment
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(
    name = "follows",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["follower_id", "following_id"]) // 중복 팔로우 방지
    ]
)
@DynamicUpdate
class Follow (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "follow_seq", unique = true, nullable = false, length = 36)
    val followSeq: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    @Comment("팔로우 하는 사람")
    val follower: User,  // 팔로우 하는 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", nullable = false)
    @Comment("팔로우 받는 사람")
    val following: User  // 팔로우 받는 사람
) : BaseTimeEntity()