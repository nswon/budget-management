package com.budgetmanagement.budgetmanagement.expense.exception;

import com.budgetmanagement.budgetmanagement.common.exception.NotFoundException;

public class ExpenseNotFoundException extends NotFoundException {
    private static final String MESSAGE = "해당 지출을 찾을 수 없습니다.";

    public ExpenseNotFoundException() {
        super(MESSAGE);
    }
}
