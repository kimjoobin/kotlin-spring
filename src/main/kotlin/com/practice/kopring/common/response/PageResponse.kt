package com.practice.kopring.common.response

data class PageResponse<T>(
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Long,
    val totalPages: Int,
    val firstPage: Boolean,
    val lastPage: Boolean
) {
    companion object {
        // 'of' 메서드는 컴패니언 객체 안에 정의하여 정적(static) 메서드처럼 사용합니다.
        // Spring Data JPA의 Page<T> 타입을 인자로 받습니다.
        fun <T> of(page: org.springframework.data.domain.Page<T>): PageResponse<T> {
            return PageResponse(
                content = page.content,
                pageNumber = page.number,
                pageSize = page.size,
                totalElements = page.totalElements,
                totalPages = page.totalPages,
                firstPage = page.isFirst,
                lastPage = page.isLast
            )
        }
    }
}
