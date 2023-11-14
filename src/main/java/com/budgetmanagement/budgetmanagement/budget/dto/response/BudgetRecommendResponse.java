package com.budgetmanagement.budgetmanagement.budget.dto.response;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategory;

public record BudgetRecommendResponse(
        String category,
        int amount
) {
    public BudgetRecommendResponse(BudgetCategory category, int amount) {
        this(category.getName(), amount);
    }
}
