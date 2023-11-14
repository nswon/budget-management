package com.budgetmanagement.budgetmanagement.budget.dto.response;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategory;

public record BudgetCategoryResponse(String category) {
    public BudgetCategoryResponse(BudgetCategory budgetCategory) {
        this(budgetCategory.getName());
    }
}
