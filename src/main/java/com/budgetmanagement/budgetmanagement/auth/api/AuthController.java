package com.budgetmanagement.budgetmanagement.auth.api;

import com.budgetmanagement.budgetmanagement.auth.domain.AuthService;
import com.budgetmanagement.budgetmanagement.auth.domain.AuthToken;
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
        AuthToken token = authService.login(request.toUserRequest());
        return ApiResponse.success(new AuthTokenResponse(token.accessToken(), token.refreshToken()));
    }

    @PostMapping("/renewal/access")
    public ApiResponse<AuthRenewalTokenResponse> generateRenewalAccessToken(@RequestBody AuthRenewalTokenRequest request) {
        AuthToken token = authService.generateRenewalAccessToken(request.toAuthToken());
        return ApiResponse.success(new AuthRenewalTokenResponse(token.accessToken()));
    }
}
