package com.practice.kopring.post.repository.query

import com.practice.kopring.post.domain.QPost.post
import com.practice.kopring.post.domain.QPostImage.postImage
import com.practice.kopring.post.dto.response.PostResponse
import com.practice.kopring.user.domain.QUser.user
import com.practice.kopring.user.dto.response.QAuthorInfo
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
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

        val post = queryFactory.select(
                Projections.fields(
                    PostResponse::class.java,
                    post.postSeq,
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
            .leftJoin(postImage).on(postImage.post.eq(post))
            .where(post.deletedAt.isNull())
            .orderBy(post.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val postSeqs = post.map { it.postSeq }

        // 4. 이미지 별도 조회 (IN 쿼리 한 번)
        val postImagesMap: Map<String, List<String>> = if (postSeqs.isNotEmpty()) {
            queryFactory
                .select(postImage.post.postSeq, postImage.path)
                .from(postImage)
                .where(postImage.post.postSeq.`in`(postSeqs))
                .orderBy(postImage.imageOrder.asc())  // 순서 보장
                .fetch()
                .groupBy({ it.get(postImage.post.postSeq)!! }, { it.get(postImage.path)!! })
        } else {
            emptyMap()
        }

        val result = post.map { postDto ->
            postDto.copy(images = postImagesMap[postDto.postSeq] ?: emptyList())
        }

        return PageImpl(result, pageable, totalCount)
    }

    // 내 글 또는 팔로잉한 사람의 글만 조회
    private fun isMyPostOrFollowingPost(currentUserSeq: String): BooleanExpression {

        return TODO("Provide the return value")
    }
}