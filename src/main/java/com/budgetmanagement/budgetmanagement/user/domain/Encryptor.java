package com.budgetmanagement.budgetmanagement.user.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Encryptor {
    private final PasswordEncoder passwordEncoder;

    public Encryptor(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encrypt(String text) {
        return passwordEncoder.encode(text);
    }
}
