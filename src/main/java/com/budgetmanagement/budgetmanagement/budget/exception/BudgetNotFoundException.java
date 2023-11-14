package com.budgetmanagement.budgetmanagement.budget.exception;

import com.budgetmanagement.budgetmanagement.common.exception.NotFoundException;

public class BudgetNotFoundException extends NotFoundException {
    private static final String MESSAGE = "예산 카테고리를 찾을 수 없습니다.";

    public BudgetNotFoundException() {
        super(MESSAGE);
    }
}
