package com.budgetmanagement.budgetmanagement.controller.auth;

import com.budgetmanagement.budgetmanagement.controller.auth.request.AuthLoginRequest;
import com.budgetmanagement.budgetmanagement.controller.auth.request.AuthRenewalTokenRequest;
import com.budgetmanagement.budgetmanagement.controller.auth.response.AuthRenewalTokenResponse;
import com.budgetmanagement.budgetmanagement.controller.auth.response.AuthTokenResponse;
import com.budgetmanagement.budgetmanagement.domain.auth.AuthService;
import com.budgetmanagement.budgetmanagement.domain.auth.AuthToken;
import com.budgetmanagement.budgetmanagement.support.response.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<AuthTokenResponse> login(@RequestBody AuthLoginRequest request) {
        AuthToken token = authService.login(request.toTarget());
        return ApiResponse.success(new AuthTokenResponse(token.accessToken(), token.refreshToken()));
    }

    @PostMapping("/renewal/access")
    public ApiResponse<AuthRenewalTokenResponse> renewAccessToken(@RequestBody AuthRenewalTokenRequest request) {
        AuthToken token = authService.renewAccessToken(request.toAuthToken());
        return ApiResponse.success(new AuthRenewalTokenResponse(token.accessToken()));
    }
}
