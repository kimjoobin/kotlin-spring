package com.practice.kopring.user.domain

import com.practice.kopring.common.domain.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 50)
    var name: String,

    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    var email: String,

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(200)")
    var password: String,

    @Column(nullable = false, length = 20)
    var phone: String,
) : BaseTimeEntity() {

}