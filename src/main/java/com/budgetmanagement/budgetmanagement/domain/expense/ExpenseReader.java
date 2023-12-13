package com.budgetmanagement.budgetmanagement.domain.expense;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpenseReader {
    private final ExpenseRepository expenseRepository;
    private final ExpenseQueryRepository expenseQueryRepository;

    public ExpenseReader(ExpenseRepository expenseRepository, ExpenseQueryRepository expenseQueryRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseQueryRepository = expenseQueryRepository;
    }

    public Expense readBy(long expenseId) {
        return expenseRepository.getById(expenseId);
    }

    public ExpenseReadResult read(long userId, String category, ExpenseRange range) {
        int totalAmount = expenseQueryRepository.getTotalAmount(userId, category, range);
        List<CategoryExpense> categories = expenseQueryRepository.getCategoryTotalList(userId, category, range);
        List<ExpenseSummary> expenses = expenseQueryRepository.getExpenses(userId, category, range);

        return new ExpenseReadResult(totalAmount, categories, expenses);
    }
}
