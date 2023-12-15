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

    public ExpenseReadResult read(long userId, String category, AmountRange range) {
        List<CategoryExpense> categories = expenseQueryRepository.getCategoryTotalAmountList(userId, category, range);
        List<ExpenseSummary> expenses = expenseQueryRepository.getExpenses(userId, category, range);
        int totalAmount = getTotalAmount(categories);

        return new ExpenseReadResult(totalAmount, categories, expenses);
    }

    private int getTotalAmount(List<CategoryExpense> categories) {
        return categories.stream()
                .mapToInt(CategoryExpense::amount)
                .sum();
    }
}
