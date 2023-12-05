package com.budgetmanagement.budgetmanagement.auth.domain;

import java.util.Optional;

public interface AuthTokenRepository {
    String save(Long userId, String refreshToken);
    Optional<String> findByUserId(Long userId);
}
