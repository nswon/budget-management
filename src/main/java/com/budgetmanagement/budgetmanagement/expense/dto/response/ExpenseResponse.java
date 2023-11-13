package com.budgetmanagement.budgetmanagement.expense.dto.response;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategoryType;

import java.time.LocalDateTime;

public record ExpenseResponse(
        LocalDateTime date,
        int amount,
        String category
) {
    public ExpenseResponse(LocalDateTime date, int amount, BudgetCategoryType category) {
        this(date, amount, category.getName());
    }
}
