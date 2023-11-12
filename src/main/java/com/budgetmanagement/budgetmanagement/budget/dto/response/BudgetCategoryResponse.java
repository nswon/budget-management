package com.budgetmanagement.budgetmanagement.budget.dto.response;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategoryType;

public record BudgetCategoryResponse(String category) {
    public BudgetCategoryResponse(BudgetCategoryType budgetCategoryType) {
        this(budgetCategoryType.getName());
    }
}
