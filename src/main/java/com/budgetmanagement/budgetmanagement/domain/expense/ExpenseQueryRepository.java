package com.budgetmanagement.budgetmanagement.domain.expense;

import java.util.List;

public interface ExpenseQueryRepository {
    int getTotalAmount(Long userId, String category, ExpenseRange range);
    List<CategoryTotal> getCategoryTotalList(Long userId, String category, ExpenseRange range);
    List<ExpenseContent> getExpenses(Long userId, String category, ExpenseRange range);
}
