package com.budgetmanagement.budgetmanagement.controller.expense.response;

import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseContent;

import java.time.LocalDateTime;

public record ExpenseDetailResponse(
        LocalDateTime date,
        int amount,
        String category,
        String memo,
        boolean isExcluded
) {
    public ExpenseDetailResponse(ExpenseContent content) {
        this(
                content.date(),
                content.amount(),
                content.category(),
                content.memo(),
                content.isExcluded()
        );
    }
}
