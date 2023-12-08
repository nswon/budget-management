package com.budgetmanagement.budgetmanagement.domain.auth;

public record AuthInfo(Long id) {

    public AuthInfo(String payload) {
        this(Long.valueOf(payload));
    }

    public String getPayload() {
        return id.toString();
    }
}
