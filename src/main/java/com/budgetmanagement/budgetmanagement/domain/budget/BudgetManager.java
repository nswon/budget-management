package com.budgetmanagement.budgetmanagement.domain.budget;

import com.budgetmanagement.budgetmanagement.domain.category.CategoryRepository;
import com.budgetmanagement.budgetmanagement.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;

@Component
public class BudgetManager {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public BudgetManager(BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Budget init(User user, BudgetAmount amount) {
        Budget budget = budgetRepository.findByUser(user)
                .orElseGet(() -> budgetRepository.save(newBudget(user, amount)));

        if(isConfiged(budget)) {
            categoryRepository.deleteAllBy(budget.getId());
        }

        return budget;
    }

    private boolean isConfiged(Budget budget) {
        return !budget.getCategories().isEmpty();
    }

    private Budget newBudget(User user, BudgetAmount amount) {
        YearMonth month = YearMonth.now();
        return new Budget(user, amount.amount(), month);
    }
}
