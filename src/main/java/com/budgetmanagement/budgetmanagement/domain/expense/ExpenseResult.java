package com.budgetmanagement.budgetmanagement.domain.expense;

import java.util.List;

public record ExpenseResult(
        int totalAmount,
        List<CategoryTotal> categoryTotals,
        List<ExpenseContent> expenses
) {
}
