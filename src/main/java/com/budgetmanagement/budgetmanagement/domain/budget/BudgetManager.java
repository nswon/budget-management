package com.budgetmanagement.budgetmanagement.domain.budget;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;

@Component
public class BudgetManager {
    private final BudgetRemover budgetRemover;

    public BudgetManager(BudgetRemover budgetRemover) {
        this.budgetRemover = budgetRemover;
    }

    @Transactional
    public void init() {
        YearMonth thisMonth = YearMonth.now();
        budgetRemover.remove(thisMonth);
    }
}
