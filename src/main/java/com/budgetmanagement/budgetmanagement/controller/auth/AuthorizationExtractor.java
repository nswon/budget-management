package com.budgetmanagement.budgetmanagement.controller.auth;

import com.budgetmanagement.budgetmanagement.support.error.ApiException;
import com.budgetmanagement.budgetmanagement.support.error.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

public class AuthorizationExtractor {
    private static final String BEARER_TYPE = "Bearer ";

    public static String extract(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new ApiException(ErrorType.EMPTY_HEADER);
        }

        validateAuthorizationFormat(authorizationHeader);
        return authorizationHeader.substring(BEARER_TYPE.length()).trim();
    }

    private static void validateAuthorizationFormat(String authorizationHeader) {
        if (!authorizationHeader.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            throw new ApiException(ErrorType.INVALID_TOKEN);
        }
    }
}
