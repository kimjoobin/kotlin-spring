package com.practice.kopring.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.Optional

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")  // ← createdBy, updatedBy 쓸거면 필수
class JpaConfig {

    @Bean
    fun auditorProvider(): AuditorAware<Long> {
        return AuditorAware {
            // TODO: 나중에 Spring Security 추가하면 실제 로그인 유저 ID 반환
            // 지금은 임시로 1L 반환
            Optional.of(1L)
        }
    }
}