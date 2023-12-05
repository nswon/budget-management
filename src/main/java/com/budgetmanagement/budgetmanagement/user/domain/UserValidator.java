package com.budgetmanagement.budgetmanagement.user.domain;

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

    public void validate(UserRequest request) {
        if(existsBy(request.accout())) {
            throw new ApiException(ErrorType.DUPLICATE_ACCOUNT);
        }
    }

    private boolean existsBy(String account) {
        return userRepository.existsByAccount(account);
    }

    public void validate(UserRequest request, User user) {
        if(isInvalidPassword(request, user)) {
            throw new ApiException(ErrorType.INVALID_PASSWORD);
        }
    }

    private boolean isInvalidPassword(UserRequest request, User user) {
        return !passwordEncoder.matches(request.password(), user.getPassword());
    }
}
