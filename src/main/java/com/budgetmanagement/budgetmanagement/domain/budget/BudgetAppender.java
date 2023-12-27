package com.budgetmanagement.budgetmanagement.domain.budget;

import com.budgetmanagement.budgetmanagement.domain.category.Category;
import com.budgetmanagement.budgetmanagement.domain.category.CategoryReader;
import com.budgetmanagement.budgetmanagement.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

@Component
public class BudgetAppender {
    private final CategoryReader categoryReader;
    private final BudgetRepository budgetRepository;

    public BudgetAppender(CategoryReader categoryReader, BudgetRepository budgetRepository) {
        this.categoryReader = categoryReader;
        this.budgetRepository = budgetRepository;
    }

    @Transactional
    public void append(User user, BudgetRequest request) {
        List<Budget> budgets = request.contents().stream()
                .map(each -> newBudget(user, each))
                .toList();

        budgetRepository.saveAll(budgets);
    }

    private Budget newBudget(User user, BudgetContent content) {
        Category category = categoryReader.readBy(content.category());
        return new Budget(user, category, content.amount(), YearMonth.now());
    }
}
