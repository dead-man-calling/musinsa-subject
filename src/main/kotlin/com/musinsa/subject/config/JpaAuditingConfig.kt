package com.musinsa.subject.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing
class JpaAuditingConfig {

    @Bean
    fun auditorAware(@Value("\${spring.application.name}") applicationName: String): AuditorAware<String> {
        return AuditorAware { Optional.of(applicationName) }
    }

}
