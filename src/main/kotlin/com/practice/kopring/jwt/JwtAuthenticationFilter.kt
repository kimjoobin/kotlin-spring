package com.practice.kopring.jwt

import com.practice.kopring.auth.service.CustomUserDetailsService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
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
            // 🔥 디버깅 로그 추가
            logger.info("=== JWT Filter ===")
            logger.info("Method: ${request.method}")
            logger.info("URI: ${request.requestURI}")

            val token = resolveToken(request)
            logger.info("Token: $token")

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
                    logger.info("JWT 토큰 검증 성공: $username")
                } else {
                    logger.info("JWT 토큰 검증 실패: $username")
                }
            }

        } catch (e: SecurityException) {
            logger.error("잘못된 JWT 서명입니다.", e)
            request.setAttribute("exception", "WRONG_TYPE_TOKEN")
        } catch (e: MalformedJwtException) {
            logger.error("유효하지 않은 구성의 JWT 토큰입니다.", e)
            request.setAttribute("exception", "UNSUPPORTED_TOKEN")
        } catch (e: ExpiredJwtException) {
            logger.error("만료된 JWT 토큰입니다.", e)
            request.setAttribute("exception", "EXPIRED_TOKEN") // 가장 중요한 예외
        } catch (e: UnsupportedJwtException) {
            logger.error("지원되지 않는 형식이거나 손상된 JWT 토큰입니다.", e)
            request.setAttribute("exception", "UNKNOWN_ERROR")
        } catch (e: IllegalArgumentException) {
            logger.error("JWT 토큰이 잘못되었습니다.", e)
            request.setAttribute("exception", "ACCESS_DENIED")
        } catch (e: Exception) {
            logger.error("JWT 토큰 처리 중 알 수 없는 오류 발생", e)
            request.setAttribute("exception", "UNKNOWN_ERROR")
        }
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI

        // JWT 검증이 필요없는 경로들
        val excludePaths = listOf(
            "/api/auth",
            "/swagger-ui",
            "/v3/api-docs",
        )

        return excludePaths.any { path.startsWith(it) }
    }

    // 토큰에서 Bearer 제거
    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

}