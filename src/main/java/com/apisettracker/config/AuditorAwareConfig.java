package com.apisettracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditorAwareConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // For testing with Postman, return a fixed value
        // In a real application, you would get this from security context
        return () -> Optional.of("system");
    }
}
