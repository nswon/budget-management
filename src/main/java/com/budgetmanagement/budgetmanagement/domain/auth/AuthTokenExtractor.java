package com.budgetmanagement.budgetmanagement.domain.auth;

import org.springframework.stereotype.Component;

@Component
public class AuthTokenExtractor {
    private final JwtTokenProvider jwtTokenProvider;

    public AuthTokenExtractor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthInfo extract(String token) {
        jwtTokenProvider.validateToken(token);
        return new AuthInfo(jwtTokenProvider.getPayload(token));
    }
}
