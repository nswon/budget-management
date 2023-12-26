package com.budgetmanagement.budgetmanagement.controller.budget.response;

import com.budgetmanagement.budgetmanagement.domain.budget.BudgetContent;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public record BudgetsRecommendResponse(List<BudgetRecommendResponse> response) {
    public static BudgetsRecommendResponse of(List<BudgetContent> budgets) {
        return budgets.stream()
                .map(each -> new BudgetRecommendResponse(each.category(), each.amount()))
                .collect(collectingAndThen(toList(), BudgetsRecommendResponse::new));
    }
}
