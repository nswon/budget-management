package com.budgetmanagement.budgetmanagement.auth.dto;


public record AuthToken(
        String accessToken,
        String refreshToken
) {
}
