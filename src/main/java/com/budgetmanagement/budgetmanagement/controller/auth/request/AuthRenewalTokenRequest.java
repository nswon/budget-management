package com.budgetmanagement.budgetmanagement.controller.auth.request;

import com.budgetmanagement.budgetmanagement.domain.auth.AuthToken;

public record AuthRenewalTokenRequest(String refreshToken) {
    public AuthToken toAuthToken() {
        return new AuthToken(null, refreshToken);
    }
}
