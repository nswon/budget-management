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

    public Expense readBy(Long expenseId) {
        return expenseRepository.getById(expenseId);
    }

    public ExpenseResult read(Long userId, String category, ExpenseRange range) {
        int totalAmount = expenseQueryRepository.getTotalAmount(userId, category, range);
        List<CategoryTotal> categoryTotals = expenseQueryRepository.getCategoryTotalList(userId, category, range);
        List<ExpenseContent> expenses = expenseQueryRepository.getExpenses(userId, category, range);

        return new ExpenseResult(totalAmount, categoryTotals, expenses);
    }
}
