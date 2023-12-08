package com.budgetmanagement.budgetmanagement.domain.expense;

import java.time.LocalDateTime;

public record ExpenseContent(
        LocalDateTime date,
        int amount,
        String category,
        String memo,
        boolean isExcluded
) {
    public ExpenseContent(Expense expense) {
        this(expense.getDate(), expense.getAmount(), expense.getCategory(), expense.getMemo(), expense.isExcluded());
    }
}
