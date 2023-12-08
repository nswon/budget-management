package com.budgetmanagement.budgetmanagement.controller.budget.request;

import com.budgetmanagement.budgetmanagement.domain.budget.BudgetAmount;
import com.budgetmanagement.budgetmanagement.domain.category.CategoryBudget;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;

public record BudgetConfigRequest(
        @Min(value = 0, message = "최소 금액은 0원입니다.")
        int budgetAmount,
        @Valid List<BudgetCategory> budgets
) {

    public record BudgetCategory(
            String category,
            @Min(value = 0, message = "최소 금액은 0원입니다.")
            int amount
    ) {}

    public BudgetAmount toAmount() {
        return new BudgetAmount(budgetAmount);
    }

    public List<CategoryBudget> toCategoryBudget() {
        return budgets.stream()
                .map(each -> new CategoryBudget(each.category, each.amount))
                .toList();
    }
}
