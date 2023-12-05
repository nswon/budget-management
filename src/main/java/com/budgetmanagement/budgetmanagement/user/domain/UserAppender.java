package com.budgetmanagement.budgetmanagement.user.domain;

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
    public void append(UserRequest request) {
        userRepository.save(newUser(request));
    }

    private User newUser(UserRequest request) {
        return User.builder()
                .account(request.accout())
                .password(encryptor.encrypt(request.password()))
                .build();
    }
}
