package com.practice.kopring.user.repository.query

import com.practice.kopring.common.response.PageResponse
import com.practice.kopring.user.dto.response.FollowUser
import org.springframework.data.domain.Pageable

interface UserRepositoryCustom {
    fun getMyFollowers(userSeq: String, pageable: Pageable): PageResponse<FollowUser>
}