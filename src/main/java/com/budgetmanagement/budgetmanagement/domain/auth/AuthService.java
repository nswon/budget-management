package com.budgetmanagement.budgetmanagement.domain.auth;

import com.budgetmanagement.budgetmanagement.domain.user.UserReader;
import com.budgetmanagement.budgetmanagement.domain.user.User;
import com.budgetmanagement.budgetmanagement.domain.user.UserTarget;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserReader userReader;
    private final AuthTokenCreator authTokenCreator;

    public AuthService(UserReader userReader, AuthTokenCreator authTokenCreator) {
        this.userReader = userReader;
        this.authTokenCreator = authTokenCreator;
    }

    public AuthToken login(UserTarget target) {
        User user = userReader.readBy(target);

        return authTokenCreator.create(new AuthInfo(user.getId()));
    }

    public AuthToken renewAccessToken(AuthToken authToken) {
        return authTokenCreator.renew(authToken);
    }
}
