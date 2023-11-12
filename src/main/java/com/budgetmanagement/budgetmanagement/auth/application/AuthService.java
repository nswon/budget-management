package com.budgetmanagement.budgetmanagement.auth.application;

import com.budgetmanagement.budgetmanagement.auth.dto.AuthToken;
import com.budgetmanagement.budgetmanagement.auth.dto.request.AuthLoginRequest;
import com.budgetmanagement.budgetmanagement.auth.dto.request.AuthRenewalTokenRequest;
import com.budgetmanagement.budgetmanagement.auth.dto.response.AuthRenewalTokenResponse;
import com.budgetmanagement.budgetmanagement.auth.dto.response.AuthTokenResponse;
import com.budgetmanagement.budgetmanagement.auth.exception.InvalidPasswordException;
import com.budgetmanagement.budgetmanagement.user.domain.User;
import com.budgetmanagement.budgetmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenCreator authTokenCreator;

    public AuthTokenResponse login(AuthLoginRequest authLoginRequest) {
        User user = userRepository.getByAccount(authLoginRequest.account());

        if(!passwordEncoder.matches(authLoginRequest.password(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        AuthToken authToken = authTokenCreator.createAuthToken(user.getId());
        return new AuthTokenResponse(authToken.accessToken(), authToken.refreshToken());
    }

    public AuthRenewalTokenResponse generateRenewalAccessToken(AuthRenewalTokenRequest renewalTokenRequest) {
        AuthToken authToken = authTokenCreator.renewAuthToken(renewalTokenRequest.refreshToken());
        return new AuthRenewalTokenResponse(authToken.accessToken());
    }

    public Long extractUserId(String accessToken) {
        return authTokenCreator.extractPayload(accessToken);
    }
}
