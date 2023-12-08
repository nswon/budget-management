package com.budgetmanagement.budgetmanagement.domain.category;

import com.budgetmanagement.budgetmanagement.domain.budget.BudgetAmount;

import java.util.List;

public interface CategoryQueryRepository {
    List<CategoryBudget> getList(BudgetAmount amount);
}
