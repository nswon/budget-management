package com.budgetmanagement.budgetmanagement.controller.expense.response;

import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseReadResult;

import java.util.List;

public record ExpensesResponse(
        int totalAmount,
        List<ExpenseCategoryTotalResponse> categories,
        List<ExpenseResponse> expenses
) {
    public static ExpensesResponse of(ExpenseReadResult result) {
        List<ExpenseCategoryTotalResponse> categories = result.categories().stream()
                .map(each -> new ExpenseCategoryTotalResponse(each.amount()))
                .toList();

        List<ExpenseResponse> expenses = result.expenses().stream()
                .map(each -> new ExpenseResponse(each.date(), each.amount(), each.category()))
                .toList();

        return new ExpensesResponse(result.totalAmount(), categories, expenses);
    }
}
