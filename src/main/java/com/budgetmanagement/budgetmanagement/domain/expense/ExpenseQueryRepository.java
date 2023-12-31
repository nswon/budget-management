package com.budgetmanagement.budgetmanagement.domain.expense;

import java.util.List;

public interface ExpenseQueryRepository {
    List<CategoryExpense> getCategoryTotalAmountList(long userId, String category, AmountRange range);
    List<ExpenseSummary> getExpenses(long userId, String category, AmountRange range);
}
