package com.practice.kopring.user.service

import com.practice.kopring.auth.dto.CustomUserDetails
import com.practice.kopring.common.enums.ErrorCode
import com.practice.kopring.common.exception.BusinessException
import com.practice.kopring.common.response.PageResponse
import com.practice.kopring.user.dto.response.FollowUser
import com.practice.kopring.user.dto.response.MyInfoResponse
import com.practice.kopring.user.repository.jpa.UserRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getMyInfo(user: CustomUserDetails): MyInfoResponse {
        val userSeq = user.getUserSeq()

        val findUser = userRepository.findByUserSeq(userSeq)
            ?: throw BusinessException(ErrorCode.NOT_FOUND_USER)

        val response = MyInfoResponse(
            userSeq = findUser.userSeq,
            username = findUser.username,
            name = findUser.name,
            email = findUser.email,
            followingCount = findUser.followingCount,
            followersCount = findUser.followerCount,
            postsCount = findUser.postCount,
            profileImageUrl = findUser.profileImage,
            bio = findUser.introduce
        )

        return response
    }

    fun getMyFollowers(
        user: CustomUserDetails,
        pageable: Pageable
    ): PageResponse<FollowUser> {
        val findUser = userRepository.findByUserSeq(user.getUserSeq())
            ?: throw BusinessException(ErrorCode.NOT_FOUND_USER)

        val result: PageResponse<FollowUser> = userRepository.getMyFollowers(findUser.userSeq, pageable)



        return TODO("Provide the return value")
    }
}