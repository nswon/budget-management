package com.budgetmanagement.budgetmanagement.auth.dto.request;

public record AuthLoginRequest(
        String account,
        String password
) {
}
