package com.budgetmanagement.budgetmanagement.user.application;

import com.budgetmanagement.budgetmanagement.user.domain.User;
import com.budgetmanagement.budgetmanagement.user.dto.request.UserJoinRequest;
import com.budgetmanagement.budgetmanagement.user.exception.DuplicateAccountException;
import com.budgetmanagement.budgetmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(UserJoinRequest userJoinRequest) {
        validateUniqueAccount(userJoinRequest);

        String encryptedPassword = passwordEncoder.encode(userJoinRequest.password());
        User user = User.builder()
                .account(userJoinRequest.account())
                .password(encryptedPassword)
                .build();

        userRepository.save(user);
    }

    private void validateUniqueAccount(UserJoinRequest userJoinRequest) {
        if (userRepository.existsByAccount(userJoinRequest.account())) {
            throw new DuplicateAccountException();
        }
    }
}
