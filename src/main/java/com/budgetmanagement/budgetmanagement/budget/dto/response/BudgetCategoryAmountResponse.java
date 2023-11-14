package com.budgetmanagement.budgetmanagement.budget.dto.response;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategory;

public record BudgetCategoryAmountResponse(
        String category,
        int amount
) {
    public BudgetCategoryAmountResponse(BudgetCategory budgetCategory, int amount) {
        this(budgetCategory.getName(), amount);
    }
}
