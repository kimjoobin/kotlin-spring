package com.practice.kopring.jwt

import com.practice.kopring.auth.service.CustomUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
    private val userDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = jwtProvider.resolveToken(request.getHeader("Authorization"))

            if (token != null && SecurityContextHolder.getContext().authentication == null) {
                val username = jwtProvider.getUsernameFromToken(token)

                if (jwtProvider.validateToken(token, username)) {
                    val userDetails = userDetailsService.loadUserByUsername(username)

                    val authToken = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                    SecurityContextHolder.getContext().authentication = authToken
                    logger.debug("JWT 토큰 검증 성공: $username")
                } else {
                    logger.debug("JWT 토큰 검증 실패: $username")
                }
            }

        } catch (ex: Exception) {
            logger.error("JWT 토큰 처리 중 오류 발생", ex)
        }
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI

        // JWT 검증이 필요없는 경로들
        val excludePaths = listOf(
            "/api/auth/**",
            "/swagger-ui/**",
            "/v3/api-docs/**"
        )

        return excludePaths.any { path.startsWith(it) }
    }
}