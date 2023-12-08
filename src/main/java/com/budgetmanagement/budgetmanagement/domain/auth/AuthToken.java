package com.budgetmanagement.budgetmanagement.domain.auth;

public record AuthToken(
        String accessToken,
        String refreshToken
) {
}
