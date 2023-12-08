package com.budgetmanagement.budgetmanagement.controller.auth.response;

public record AuthTokenResponse(
        String accessToken,
        String refreshToken
) {
}
