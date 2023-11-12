package com.budgetmanagement.budgetmanagement.budget.dto.response;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategoryType;

public record BudgetRecommendationResponse(
        String category,
        int amount
) {
    public BudgetRecommendationResponse(BudgetCategoryType categoryType, int amount) {
        this(categoryType.getName(), amount);
    }
}
