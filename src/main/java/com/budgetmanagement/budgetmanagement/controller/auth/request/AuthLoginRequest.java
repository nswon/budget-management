package com.budgetmanagement.budgetmanagement.controller.auth.request;

import com.budgetmanagement.budgetmanagement.domain.user.UserTarget;

public record AuthLoginRequest(
        String account,
        String password
) {
    public UserTarget toTarget() {
        return new UserTarget(account, password);
    }
}
