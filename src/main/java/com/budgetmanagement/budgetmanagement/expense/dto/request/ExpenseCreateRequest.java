package com.budgetmanagement.budgetmanagement.expense.dto.request;

public record ExpenseCreateRequest(
        String date,
        int amount,
        String category,
        String memo
) {
}
