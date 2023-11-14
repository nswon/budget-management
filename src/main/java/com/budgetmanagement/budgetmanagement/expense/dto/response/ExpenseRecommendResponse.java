package com.budgetmanagement.budgetmanagement.expense.dto.response;

import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetCategoryAmountResponse;

import java.util.List;

public record ExpenseRecommendResponse(
        int totalAmount,
        List<BudgetCategoryAmountResponse> categoryAmounts,
        String message
) {
}
