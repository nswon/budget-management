package com.budgetmanagement.budgetmanagement.user.domain;

import org.springframework.stereotype.Component;

@Component
public class UserReader {
    private final UserValidator userValidator;
    private final UserRepository userRepository;

    public UserReader(UserValidator userValidator, UserRepository userRepository) {
        this.userValidator = userValidator;
        this.userRepository = userRepository;
    }

    public User read(UserRequest request) {
        User user = userRepository.getByAccount(request.accout());

        userValidator.validate(request, user);

        return user;
    }
}
