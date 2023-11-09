package com.budgetmanagement.budgetmanagement.user.exception;

import com.budgetmanagement.budgetmanagement.common.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    private static final String MESSAGE = "존재하지 않는 유저입니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
