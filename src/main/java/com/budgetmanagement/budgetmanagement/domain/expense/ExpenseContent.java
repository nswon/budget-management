package com.budgetmanagement.budgetmanagement.domain.expense;

import java.time.LocalDateTime;

public record ExpenseContent(
        LocalDateTime date,
        int amount,
        String category,
        String memo,
        boolean isExcluded
) {
    public ExpenseContent(LocalDateTime date, int amount, String category, String memo) {
        this(date, amount, category, memo, false);
    }

    public ExpenseContent(Expense expense) {
        this(expense.getDate(), expense.getAmount(), expense.getCategory().getName(), expense.getMemo(), expense.isExcluded());
    }
}
