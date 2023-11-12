package com.budgetmanagement.budgetmanagement.budget.repository;

import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetRecommendationResponse;

import java.util.List;

public interface BudgetCategoryQRepository {
    List<BudgetRecommendationResponse> getRecommendationBudget(int totalAmount, int userCount);
}
