package com.budgetmanagement.budgetmanagement.user.exception;

import com.budgetmanagement.budgetmanagement.common.exception.BadRequestException;

public class DuplicateAccountException extends BadRequestException {
    private static final String MESSAGE = "중복된 계정입니다.";

    public DuplicateAccountException() {
        super(MESSAGE);
    }
}
