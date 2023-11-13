package com.budgetmanagement.budgetmanagement.expense.dto.response;

import java.util.List;

public record ExpensesResponse(
        int totalAmount,
        List<ExpenseCategoryTotalAmountResponse> categoryTotalAmounts,
        List<ExpenseResponse> expenses
) {
}
