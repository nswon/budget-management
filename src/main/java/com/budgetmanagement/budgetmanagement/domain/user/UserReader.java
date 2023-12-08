package com.budgetmanagement.budgetmanagement.domain.user;

import org.springframework.stereotype.Component;

@Component
public class UserReader {
    private final UserValidator userValidator;
    private final UserRepository userRepository;

    public UserReader(UserValidator userValidator, UserRepository userRepository) {
        this.userValidator = userValidator;
        this.userRepository = userRepository;
    }

    public User readBy(UserTarget target) {
        User user = userRepository.getByAccount(target.accout());

        userValidator.validate(target, user);

        return user;
    }

    public User readBy(Long id) {
        return userRepository.getById(id);
    }
}
