package com.budgetmanagement.budgetmanagement.domain.budget;

import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Component
public class BudgetRemover {
    private final BudgetRepository budgetRepository;

    public BudgetRemover(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public void remove(YearMonth date) {
        budgetRepository.deleteAllBy(date);
    }
}
