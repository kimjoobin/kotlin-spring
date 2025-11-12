package com.practice.kopring.user.domain

import com.practice.kopring.auth.dto.CreateUserRequest
import com.practice.kopring.common.domain.BaseTimeEntity
import com.practice.kopring.post.domain.Post
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.hibernate.annotations.Comment

@Entity
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false, length = 50)
    @Comment("로그인 ID")
    var username: String,

    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    var email: String,

    @Column(nullable = false, columnDefinition = "VARCHAR(200)")
    var password: String,
    
    @Column(nullable = false, length = 50)
    @Comment("실제 이름")
    var name: String,

    @Column(length = 20)
    var phone: String? = null,

    @Column(length = 250)
    @Comment("소개")
    var introduce: String? = null,

    @Column(length = 200)
    @Comment("웹사이트")
    var website: String? = null,

    @Comment("팔로워 수")
    var followerCount: Int = 0,

    @Comment("팔로잉 수")
    var followingCount: Int = 0,

    @Comment("게시글 좋아요수")
    var postCount: Int = 0,

    @Column(length = 500)
    @Comment("프로필 사진 url")
    var profileImage: String? = null,

    @OneToMany(mappedBy = "user", cascade = [(CascadeType.ALL)])
    var posts: MutableList<Post> = mutableListOf()
) : BaseTimeEntity() {
    fun updateProfile(name: String, introduce: String?, website: String?) {
        this.name = name
        this.introduce = introduce
        this.website = website
    }

    fun increasePostCount() {
        this.postCount++
    }

    fun decreasePostCount() {
        if (this.postCount > 0) this.postCount--
    }

    fun increaseFollowerCount() {
        this.followerCount++
    }

    fun decreaseFollowerCount() {
        if (this.followerCount > 0) this.followerCount--
    }

    fun increaseFollowingCount() {
        this.followingCount++
    }

    fun decreaseFollowingCount() {
        if (this.followingCount > 0) this.followingCount--
    }
}