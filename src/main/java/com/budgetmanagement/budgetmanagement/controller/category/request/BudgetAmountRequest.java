package com.budgetmanagement.budgetmanagement.controller.category.request;

import com.budgetmanagement.budgetmanagement.domain.budget.BudgetAmount;

public record BudgetAmountRequest(int amount) {
    public BudgetAmount toAmount() {
        return new BudgetAmount(amount);
    }
}
