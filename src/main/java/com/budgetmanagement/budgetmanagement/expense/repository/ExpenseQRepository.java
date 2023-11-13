package com.budgetmanagement.budgetmanagement.expense.repository;

import com.budgetmanagement.budgetmanagement.expense.dto.response.ExpenseCategoryTotalAmountResponse;
import com.budgetmanagement.budgetmanagement.expense.dto.response.ExpenseResponse;

import java.util.List;

public interface ExpenseQRepository {
    int getTotalAmount(Long userId, String category, int minAmount, int maxAmount);
    List<ExpenseCategoryTotalAmountResponse> getCategoryTotalAmounts(Long userId, String category, int minAmount, int maxAmount);
    List<ExpenseResponse> getExpenses(Long userId, String category, int minAmount, int maxAmount);
}
