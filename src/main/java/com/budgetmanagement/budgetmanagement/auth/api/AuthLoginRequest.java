package com.budgetmanagement.budgetmanagement.auth.api;

import com.budgetmanagement.budgetmanagement.user.domain.UserRequest;

public record AuthLoginRequest(
        String account,
        String password
) {
    public UserRequest toUserRequest() {
        return new UserRequest(account, password);
    }
}
