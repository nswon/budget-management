package com.budgetmanagement.budgetmanagement.auth.exception;

import com.budgetmanagement.budgetmanagement.common.exception.BadRequestException;

public class InvalidPasswordException extends BadRequestException {
    private static final String MESSAGE = "잘못된 비밀번호입니다.";

    public InvalidPasswordException() {
        super(MESSAGE);
    }
}
