package com.budgetmanagement.budgetmanagement.auth.domain;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class AuthTokenRepositoryImpl implements AuthTokenRepository {
    private final Map<Long, String> tokenRepository;

    public AuthTokenRepositoryImpl(Map<Long, String> tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public String save(Long userId, String token) {
        tokenRepository.put(userId, token);
        return token;
    }

    @Override
    public Optional<String> findByUserId(Long userId) {
        return Optional.ofNullable(tokenRepository.get(userId));
    }
}
