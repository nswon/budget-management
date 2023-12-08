package com.budgetmanagement.budgetmanagement.controller.expense.request;

import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseRecord;

public record ExpenseUpdateRequest(
        String date,
        int amount,
        String category,
        String memo
) {
    public ExpenseRecord toRecord() {
        return new ExpenseRecord(date, amount, category, memo);
    }
}
