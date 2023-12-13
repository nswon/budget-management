package com.budgetmanagement.budgetmanagement.domain.budget;

import java.util.List;

public interface BudgetQueryRepository {
    List<BudgetContent> getList(BudgetAmount amount);
}
