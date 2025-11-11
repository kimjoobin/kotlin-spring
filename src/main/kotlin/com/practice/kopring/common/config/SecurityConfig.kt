package com.practice.kopring.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
            http
                .csrf { it.disable() }  // CSRF 비활성화 (REST API용)
                .authorizeHttpRequests { auth ->
                    auth
                        .requestMatchers(
                            "/",
                            "/auth/**",          // 회원가입, 로그인 페이지
                            "/css/**",           // 정적 리소스
                            "/js/**",
                            "/images/**",
                        ).permitAll()
                        .anyRequest().authenticated()  // 나머지는 인증 필요
                }
                .formLogin { form ->
                    form
                        .loginPage("/auth/login")           // 커스텀 로그인 페이지
                        .loginProcessingUrl("/auth/login")  // 로그인 처리 URL
                        .defaultSuccessUrl("/posts", true)  // 로그인 성공시 이동
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                }
                .logout { logout ->
                    logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                }
                .sessionManagement { session ->
                    session
                        .maximumSessions(1)  // 동시 로그인 1개만 허용
                        .maxSessionsPreventsLogin(false)  // 새 로그인이 기존 세션 무효화
                }
                .headers { headers ->
                    headers.frameOptions { it.disable() }  // H2 Console용
                }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}