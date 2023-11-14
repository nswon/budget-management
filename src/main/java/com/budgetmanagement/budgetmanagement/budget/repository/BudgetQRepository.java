package com.budgetmanagement.budgetmanagement.budget.repository;

import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetCategoryAmountResponse;
import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetRecommendResponse;
import com.budgetmanagement.budgetmanagement.user.domain.User;

import java.util.List;

public interface BudgetQRepository {
    List<BudgetRecommendResponse> getRecommendationBudget(int totalAmount, int userCount);
    List<BudgetCategoryAmountResponse> getCategoryAmounts(int amount, User user);
}
