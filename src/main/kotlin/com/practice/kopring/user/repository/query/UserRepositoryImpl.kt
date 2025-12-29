package com.practice.kopring.user.repository.query

import com.practice.kopring.common.response.PageResponse
import com.practice.kopring.user.dto.response.FollowUser
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : UserRepositoryCustom {

    override fun getMyFollowers(
        userSeq: String,
        pageable: Pageable
    ): PageResponse<FollowUser> {
        TODO("Not yet implemented")
    }

}