package com.budgetmanagement.budgetmanagement.expense.dto.response;

import com.budgetmanagement.budgetmanagement.expense.domain.Expense;

import java.time.LocalDateTime;

public record ExpenseDetailResponse(
        LocalDateTime date,
        int amount,
        String category,
        String memo,
        boolean isExcluded
) {
    public ExpenseDetailResponse(Expense expense) {
        this(
                expense.getDate(),
                expense.getAmount(),
                expense.getCategoryName(),
                expense.getMemo(),
                expense.isExcluded()
        );
    }
}
