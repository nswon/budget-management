package com.budgetmanagement.budgetmanagement.auth.exception;

import com.budgetmanagement.budgetmanagement.common.exception.BadRequestException;

public class EmptyAuthorizationHeaderException extends BadRequestException {
    private static final String MESSAGE = "인증 헤더의 값이 비어있습니다.";

    public EmptyAuthorizationHeaderException() {
        super(MESSAGE);
    }
}
