package com.budgetmanagement.budgetmanagement.auth.repository.impl;

import com.budgetmanagement.budgetmanagement.auth.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthTokenRepositoryImpl implements AuthTokenRepository {
    private final Map<Long, String> tokenRepository;

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
