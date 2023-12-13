package com.budgetmanagement.budgetmanagement.domain.user;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserAppender {
    private final Encryptor encryptor;
    private final UserRepository userRepository;

    public UserAppender(Encryptor encryptor, UserRepository userRepository) {
        this.encryptor = encryptor;
        this.userRepository = userRepository;
    }

    @Transactional
    public void append(UserTarget target) {
        userRepository.save(newUser(target));
    }

    private User newUser(UserTarget target) {
        return new User(target.account(), encryptor.encrypt(target.password()));
    }
}
