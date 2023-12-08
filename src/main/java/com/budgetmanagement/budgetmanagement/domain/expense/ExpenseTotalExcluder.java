package com.budgetmanagement.budgetmanagement.domain.expense;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ExpenseTotalExcluder {
    private final ExpenseReader expenseReader;

    public ExpenseTotalExcluder(ExpenseReader expenseReader) {
        this.expenseReader = expenseReader;
    }

    @Transactional
    public void exclude(Long expenseId) {
        Expense expense = expenseReader.readBy(expenseId);
        expense.exclude();
    }
}
