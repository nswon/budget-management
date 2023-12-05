package com.budgetmanagement.budgetmanagement.auth.api;

import com.budgetmanagement.budgetmanagement.auth.domain.AuthToken;

public record AuthRenewalTokenRequest(String refreshToken) {
    public AuthToken toAuthToken() {
        return new AuthToken(null, refreshToken);
    }
}
