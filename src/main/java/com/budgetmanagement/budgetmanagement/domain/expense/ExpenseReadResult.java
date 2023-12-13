package com.budgetmanagement.budgetmanagement.domain.expense;

import java.util.List;

public record ExpenseReadResult(
        int totalAmount,
        List<CategoryExpense> categories,
        List<ExpenseSummary> expenses
) {
}
