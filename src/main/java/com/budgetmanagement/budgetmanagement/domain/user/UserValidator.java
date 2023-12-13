package com.budgetmanagement.budgetmanagement.domain.user;

import com.budgetmanagement.budgetmanagement.support.error.ApiException;
import com.budgetmanagement.budgetmanagement.support.error.ErrorType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserValidator(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void validate(UserTarget target) {
        if(existsBy(target.account())) {
            throw new ApiException(ErrorType.DUPLICATE_ACCOUNT);
        }
    }

    private boolean existsBy(String account) {
        return userRepository.existsByAccount(account);
    }

    public void validate(UserTarget target, User user) {
        if(isInvalidPassword(target, user)) {
            throw new ApiException(ErrorType.INVALID_PASSWORD);
        }
    }

    private boolean isInvalidPassword(UserTarget target, User user) {
        return !passwordEncoder.matches(target.password(), user.getPassword());
    }
}
