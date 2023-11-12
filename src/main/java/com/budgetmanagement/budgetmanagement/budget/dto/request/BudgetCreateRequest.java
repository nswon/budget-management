package com.budgetmanagement.budgetmanagement.budget.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;

public record BudgetCreateRequest(@Valid List<BudgetByCategory> budgets) {

    public record BudgetByCategory(
            String category,
            @Min(value = 0, message = "최소 금액은 0원입니다.")
            int amount
    ) {}

    public double getTotalAmount() {
        return budgets.stream()
                .mapToInt(BudgetByCategory::amount)
                .sum();
    }
}


