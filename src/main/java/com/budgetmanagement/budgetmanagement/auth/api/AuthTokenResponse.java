package com.budgetmanagement.budgetmanagement.auth.api;

public record AuthTokenResponse(
        String accessToken,
        String refreshToken
) {
}
