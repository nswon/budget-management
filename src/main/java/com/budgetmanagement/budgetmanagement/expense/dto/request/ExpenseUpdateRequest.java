package com.budgetmanagement.budgetmanagement.expense.dto.request;

public record ExpenseUpdateRequest(
        String date,
        int amount,
        String category,
        String memo
) {
}
