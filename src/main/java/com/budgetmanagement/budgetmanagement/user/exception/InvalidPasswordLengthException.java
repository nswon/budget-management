package com.budgetmanagement.budgetmanagement.user.exception;

import com.budgetmanagement.budgetmanagement.common.exception.BadRequestException;

public class InvalidPasswordLengthException extends BadRequestException {
    private static final String MESSAGE = "비밀번호는 길이가 최소 10자 이상이어야 합니다.";

    public InvalidPasswordLengthException() {
        super(MESSAGE);
    }
}
