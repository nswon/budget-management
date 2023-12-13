package com.budgetmanagement.budgetmanagement.domain.budget;

import com.budgetmanagement.budgetmanagement.domain.user.User;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Component
public class BudgetRemover {
    private final BudgetRepository budgetRepository;

    public BudgetRemover(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public void remove(User user, YearMonth date) {
        budgetRepository.deleteAllBy(user.getId(), date);
    }
}
