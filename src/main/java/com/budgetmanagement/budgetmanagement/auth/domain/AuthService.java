package com.budgetmanagement.budgetmanagement.auth.domain;

import com.budgetmanagement.budgetmanagement.user.domain.UserReader;
import com.budgetmanagement.budgetmanagement.user.domain.User;
import com.budgetmanagement.budgetmanagement.user.domain.UserRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserReader userReader;
    private final AuthTokenCreator authTokenCreator;

    public AuthService(UserReader userReader, AuthTokenCreator authTokenCreator) {
        this.userReader = userReader;
        this.authTokenCreator = authTokenCreator;
    }

    public AuthToken login(UserRequest request) {
        User user = userReader.read(request);

        return authTokenCreator.createAuthToken(user.getId());
    }

    public AuthToken generateRenewalAccessToken(AuthToken authToken) {
        return authTokenCreator.renewAuthToken(authToken);
    }

    public Long extractUserId(String accessToken) {
        return authTokenCreator.extractPayload(accessToken);
    }
}
