package com.budgetmanagement.budgetmanagement.domain.expense;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ExpenseExcluder {
    private final ExpenseReader expenseReader;

    public ExpenseExcluder(ExpenseReader expenseReader) {
        this.expenseReader = expenseReader;
    }

    @Transactional
    public void exclude(long expenseId) {
        Expense expense = expenseReader.readBy(expenseId);
        expense.exclude();
    }
}
