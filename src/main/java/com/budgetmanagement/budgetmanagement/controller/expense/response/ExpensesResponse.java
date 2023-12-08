package com.budgetmanagement.budgetmanagement.controller.expense.response;

import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseResult;

import java.util.List;

public record ExpensesResponse(
        int totalAmount,
        List<ExpenseCategoryTotalResponse> categoryTotals,
        List<ExpenseResponse> expenses
) {
    public static ExpensesResponse of(ExpenseResult result) {
        List<ExpenseCategoryTotalResponse> categoryTotals = result.categoryTotals().stream()
                .map(each -> new ExpenseCategoryTotalResponse(each.amount()))
                .toList();

        List<ExpenseResponse> expenses = result.expenses().stream()
                .map(each -> new ExpenseResponse(each.date(), each.amount(), each.category()))
                .toList();

        return new ExpensesResponse(result.totalAmount(), categoryTotals, expenses);
    }
}
