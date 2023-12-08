package com.budgetmanagement.budgetmanagement.domain.auth;

import org.springframework.stereotype.Component;

@Component
public class AuthTokenCreator {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthTokenRepository authTokenRepository;

    public AuthTokenCreator(JwtTokenProvider jwtTokenProvider, AuthTokenRepository authTokenRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authTokenRepository = authTokenRepository;
    }

    public AuthToken create(AuthInfo info) {
        String accessToken = jwtTokenProvider.createAccessToken(info.getPayload());
        String refreshToken = createRefreshToken(info);
        return new AuthToken(accessToken, refreshToken);
    }

    private String createRefreshToken(AuthInfo info) {
        return authTokenRepository.findByUserId(info.id())
                .orElseGet(() -> {
                    String refreshToken = jwtTokenProvider.createRefreshToken(info.getPayload());
                    return authTokenRepository.save(info.id(), refreshToken);
                });
    }

    public AuthToken renew(AuthToken authToken) {
        String refreshToken = authToken.refreshToken();
        jwtTokenProvider.validateToken(refreshToken);

        String userId = jwtTokenProvider.getPayload(refreshToken);
        String newAccessToken = jwtTokenProvider.createAccessToken(userId);

        return new AuthToken(newAccessToken, refreshToken);
    }
}
