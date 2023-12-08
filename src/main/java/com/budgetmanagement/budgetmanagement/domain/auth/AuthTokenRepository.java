package com.budgetmanagement.budgetmanagement.domain.auth;

import java.util.Optional;

public interface AuthTokenRepository {
    String save(Long userId, String refreshToken);
    Optional<String> findByUserId(Long userId);
}
