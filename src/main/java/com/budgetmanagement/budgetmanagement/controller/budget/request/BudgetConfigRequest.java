package com.budgetmanagement.budgetmanagement.controller.budget.request;

import com.budgetmanagement.budgetmanagement.domain.budget.BudgetContent;
import com.budgetmanagement.budgetmanagement.domain.budget.BudgetRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;

public record BudgetConfigRequest(@Valid List<Budget> budgets) {

    public record Budget(
            String category,
            @Min(value = 0, message = "최소 금액은 0원입니다.")
            int amount
    ) {}

    public BudgetRequest toRequest() {
        List<BudgetContent> contents = budgets.stream()
                .map(each -> new BudgetContent(each.category, each.amount))
                .toList();

        return new BudgetRequest(contents);
    }
}
