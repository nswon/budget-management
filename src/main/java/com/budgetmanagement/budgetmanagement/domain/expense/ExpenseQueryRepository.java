package com.budgetmanagement.budgetmanagement.domain.expense;

import java.util.List;

public interface ExpenseQueryRepository {
    int getTotalAmount(long userId, String category, ExpenseRange range);
    List<CategoryExpense> getCategoryTotalList(long userId, String category, ExpenseRange range);
    List<ExpenseSummary> getExpenses(long userId, String category, ExpenseRange range);
}
