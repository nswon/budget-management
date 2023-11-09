package com.budgetmanagement.budgetmanagement.auth.dto.response;

public record AuthTokenResponse(
        String accessToken,
        String refreshToken
) {
}
