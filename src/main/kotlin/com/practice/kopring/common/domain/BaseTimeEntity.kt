package com.practice.kopring.common.domain

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    var createdBy: Long? = null

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null

    @LastModifiedBy
    var updatedBy: Long? = null

    var deletedAt: LocalDateTime? = null

    var deletedBy: Long? = null

    fun delete(deletedBy: Long) {
        this.deletedAt = LocalDateTime.now()
        this.deletedBy = deletedBy
    }
}