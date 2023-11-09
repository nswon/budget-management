package com.budgetmanagement.budgetmanagement.auth.controller;

import com.budgetmanagement.budgetmanagement.auth.application.AuthService;
import com.budgetmanagement.budgetmanagement.auth.dto.request.AuthLoginRequest;
import com.budgetmanagement.budgetmanagement.auth.dto.request.AuthRenewalTokenRequest;
import com.budgetmanagement.budgetmanagement.auth.dto.response.AuthRenewalTokenResponse;
import com.budgetmanagement.budgetmanagement.auth.dto.response.AuthTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(@RequestBody AuthLoginRequest authLoginRequest) {
        AuthTokenResponse authTokenResponse = authService.login(authLoginRequest);
        return ResponseEntity.ok(authTokenResponse);
    }

    @PostMapping("/renewal/access")
    public ResponseEntity<AuthRenewalTokenResponse> generateRenewalAccessToken(@RequestBody AuthRenewalTokenRequest renewalTokenRequest) {
        AuthRenewalTokenResponse authRenewalTokenResponse = authService.generateRenewalAccessToken(renewalTokenRequest);
        return ResponseEntity.ok(authRenewalTokenResponse);
    }
}
