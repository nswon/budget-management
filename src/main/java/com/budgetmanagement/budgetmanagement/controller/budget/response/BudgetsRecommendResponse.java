package com.budgetmanagement.budgetmanagement.controller.budget.response;

import com.budgetmanagement.budgetmanagement.domain.budget.BudgetContent;

import java.util.List;

public record BudgetsRecommendResponse(List<BudgetRecommendResponse> response) {
    public static BudgetsRecommendResponse of(List<BudgetContent> budgets) {
        List<BudgetRecommendResponse> response = budgets.stream()
                .map(each -> new BudgetRecommendResponse(each.category(), each.amount()))
                .toList();
        return new BudgetsRecommendResponse(response);
    }
}
