package com.practice.kopring.common.config

import com.practice.kopring.auth.service.CustomUserDetailsService
import com.practice.kopring.jwt.JwtAccessDenied
import com.practice.kopring.jwt.JwtAuthenticationFilter
import com.practice.kopring.jwt.JwtEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customUserDetailsService: CustomUserDetailsService,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val jwtAuthenticationEntryPoint: JwtEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDenied,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http.cors { it.configurationSource(corsConfigurationSource()) }
        http.csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(
                        "/api/auth/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                    )
                    .permitAll()
                    .anyRequest().authenticated()   // 이거 없으면 모든 요청이 거부됨
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

            // 🚨 인증/인가 예외 핸들러 등록
            .exceptionHandling { handling ->
                // 401 Unauthorized 발생 시 호출
                handling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                // 403 Forbidden 발생 시 호출
                handling.accessDeniedHandler(jwtAccessDeniedHandler)
            }


        return http.build()
    }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(customUserDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        // allowedOriginPatterns = listOf("*")은 allowCredentials = true와 같이 쓸 수 없음!
        configuration.allowedOrigins = listOf("http://localhost:3000")
        // 허용할 HTTP Method
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        // 허용할 헤더 (Authorization, Content-Type 등)
        configuration.allowedHeaders = listOf("*")
        // 쿠키나 인증 정보(Authorization 헤더)를 포함한 요청 허용
        configuration.allowCredentials = true
        configuration.exposedHeaders = listOf("Authorization")
        configuration.maxAge = 3600L // 캐싱

        // 모든 경로(/**)에 대해 위 설정 적용
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)

        return source
    }
}