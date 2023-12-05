package com.budgetmanagement.budgetmanagement.auth.domain;


public record AuthToken(
        String accessToken,
        String refreshToken
) {
}
