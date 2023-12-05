package com.budgetmanagement.budgetmanagement.user.domain;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserValidator userValidator;
    private final UserAppender userAppender;

    public UserService(UserValidator userValidator, UserAppender userAppender) {
        this.userValidator = userValidator;
        this.userAppender = userAppender;
    }

    public void join(UserRequest request) {
        userValidator.validate(request);
        userAppender.append(request);
    }
}
