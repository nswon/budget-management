package com.budgetmanagement.budgetmanagement.user.exception;

import com.budgetmanagement.budgetmanagement.common.exception.BadRequestException;

public class InvalidPasswordFormatException extends BadRequestException {
    private static final String MESSAGE = "비밀번호는 숫자, 문자, 특수문자 중 2가지 이상 포함해야 합니다.";

    public InvalidPasswordFormatException() {
        super(MESSAGE);
    }
}
