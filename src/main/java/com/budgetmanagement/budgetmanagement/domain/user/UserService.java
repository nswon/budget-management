package com.budgetmanagement.budgetmanagement.domain.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserValidator userValidator;
    private final UserAppender userAppender;

    public UserService(UserValidator userValidator, UserAppender userAppender) {
        this.userValidator = userValidator;
        this.userAppender = userAppender;
    }

    public void join(UserTarget target) {
        userValidator.validate(target);
        userAppender.append(target);
    }
}
