package com.budgetmanagement.budgetmanagement.config;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class ExpiringMapConfig {

    //refresh token 저장소 빈 등록
    @Bean
    public Map<Long, String> tokenRepository() {
        return ExpiringMap.builder()
                .maxSize(1000)
                .expirationPolicy(ExpirationPolicy.CREATED)
                .expiration(15, TimeUnit.DAYS)
                .build();
    }
}
