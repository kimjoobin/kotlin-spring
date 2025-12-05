package com.practice.kopring.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @Value("\${jwt.key}")
    private var secretKey: String
) {

    // 클래스 레벨의 정적(static) 멤버를 만들기 위한 방법
    companion object {

        private val ACCESS_TOKEN_EXPIRATION: Long = 30 * 60 * 1000

        private val REFRESH_TOKEN_EXPIRATION: Long = 14 * 24 * 60 * 60 * 1000
    }

    private fun getSecretKey(): SecretKey {
        return Keys.hmacShaKeyFor(secretKey.toByteArray())
    }

    // Access Token 생성
    fun generateAccessToken(userId: String, username: String): String {
        return createToken(
            claims = mapOf(
                "userId" to userId,
                "username" to username,
                "type" to "access"
            ),
            subject = username,
            expiration = ACCESS_TOKEN_EXPIRATION,
        )
    }

    // Refresh Token 생성
    fun generateRefreshToken(userId: String, username: String): String {
        return createToken(
            claims = mapOf(
                "userId" to userId,
                "username" to username,
                "type" to "refresh"
            ),
            subject = username,
            expiration = REFRESH_TOKEN_EXPIRATION,
        )
    }

    // 토큰 생성 공통 메소드
    private fun createToken(claims: Map<String, Any>, subject: String, expiration: Long): String {
        val now = Date()
        val expiryDate = Date(now.time + expiration)

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(getSecretKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    // 토큰에서 사용자명 추출
    fun getUsernameFromToken(token: String): String {
        return getClaimsFromToken(token).subject
    }

    // 토큰에서 사용자 ID 추출
    fun getUserIdFromToken(token: String): Long {
        val claims = getClaimsFromToken(token)
        return claims["userId"].toString().toLong()
    }

    // 토큰에서 타입 추출 (access/refresh)
    fun getTokenType(token: String): String {
        val claims = getClaimsFromToken(token)
        return claims["type"].toString()
    }

    // 토큰에서 만료일 추출
    fun getExpirationDateFromToken(token: String): Date {
        return getClaimsFromToken(token).expiration
    }

    // 토큰에서 Claims 추출
    private fun getClaimsFromToken(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(getSecretKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    // 토큰 만료 여부 확인
    fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    // 토큰 유효성 검증
    fun validateToken(token: String, username: String): Boolean {
        return try {
            val tokenUsername = getUsernameFromToken(token)
            tokenUsername == username && !isTokenExpired(token)
        } catch (e: Exception) {
            false
        }
    }

    // Access Token 만료 시간 반환 (초 단위)
    fun getAccessTokenExpirationInSeconds(): Long {
        return ACCESS_TOKEN_EXPIRATION / 1000
    }

    // Refresh Token 만료 시간 반환 (초 단위)
    fun getRefreshTokenExpirationInSeconds(): Long {
        return REFRESH_TOKEN_EXPIRATION / 1000
    }
}