package com.budgetmanagement.budgetmanagement.support.error;

import lombok.Getter;

@Getter
public class ErrorMessage {
    private final String message;
    private final Object data;

    public ErrorMessage(ErrorType errorType) {
        this.message = errorType.getMessage();
        this.data = null;
    }

    public ErrorMessage(ErrorType errorType, Object data) {
        this.message = errorType.getMessage();
        this.data = data;
    }
}
