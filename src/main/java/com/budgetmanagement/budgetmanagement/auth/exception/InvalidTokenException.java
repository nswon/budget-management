package com.budgetmanagement.budgetmanagement.auth.exception;

import com.budgetmanagement.budgetmanagement.common.exception.UnauthorizedException;

public class InvalidTokenException extends UnauthorizedException {
    private static final String MESSAGE = "잘못된 토큰입니다.";

    public InvalidTokenException() {
        super(MESSAGE);
    }
}
