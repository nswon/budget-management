package com.budgetmanagement.budgetmanagement.auth.domain;

import org.springframework.stereotype.Component;

@Component
public class AuthTokenCreator {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthTokenRepository authTokenRepository;

    public AuthTokenCreator(JwtTokenProvider jwtTokenProvider, AuthTokenRepository authTokenRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authTokenRepository = authTokenRepository;
    }

    public AuthToken createAuthToken(Long userId) {
        String accessToken = jwtTokenProvider.createAccessToken(userId.toString());
        String refreshToken = createRefreshToken(userId);
        return new AuthToken(accessToken, refreshToken);
    }

    private String createRefreshToken(Long userId) {
        return authTokenRepository.findByUserId(userId)
                .orElseGet(() -> {
                    String refreshToken = jwtTokenProvider.createRefreshToken(userId.toString());
                    return authTokenRepository.save(userId, refreshToken);
                });
    }

    public AuthToken renewAuthToken(AuthToken authToken) {
        String refreshToken = authToken.refreshToken();
        jwtTokenProvider.validateToken(refreshToken);

        String userId = jwtTokenProvider.getPayload(refreshToken);
        String newAccessToken = jwtTokenProvider.createAccessToken(userId);

        return new AuthToken(newAccessToken, refreshToken);
    }

    public Long extractPayload(String accessToken) {
        jwtTokenProvider.validateToken(accessToken);
        return Long.valueOf(jwtTokenProvider.getPayload(accessToken));
    }
}
