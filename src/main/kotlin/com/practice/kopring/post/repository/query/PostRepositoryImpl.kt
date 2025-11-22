package com.practice.kopring.post.repository.query

import com.practice.kopring.post.domain.QPost.post
import com.practice.kopring.post.dto.response.PostResponse
import com.practice.kopring.post.dto.response.QPostResponse
import com.practice.kopring.user.domain.QUser.user
import com.practice.kopring.user.dto.response.QAuthorInfo
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : PostRepositoryCustom {

    override fun getPostList(pageable: Pageable): Page<PostResponse> {
        val totalCount = queryFactory.select(post.count())
            .from(post)
            .where(post.deletedAt.isNull())
            .fetchOne() ?: 0L

        if (totalCount == 0L) {
            return PageImpl(emptyList(), pageable, totalCount)
        }

        val result = queryFactory.select(
                QPostResponse(
                    post.postSeq,
                    post.imageUrl,
                    post.caption,
                    post.location,
                    post.likeCount,
                    post.commentCount,
                    QAuthorInfo(
                        user.userSeq,
                        user.username,
                        user.email,
                        user.profileImage
                    ),
        post.createdAt,
        post.updatedAt
                )
            )
            .from(post)
            .innerJoin(user).on(post.user.eq(user))
            .where(post.deletedAt.isNull())
            .orderBy(post.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        return PageImpl(result, pageable, totalCount)
    }
}