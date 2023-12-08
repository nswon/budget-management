package com.budgetmanagement.budgetmanagement.domain.budget;

import com.budgetmanagement.budgetmanagement.domain.category.CategoryAppender;
import com.budgetmanagement.budgetmanagement.domain.category.CategoryBudget;
import com.budgetmanagement.budgetmanagement.domain.user.User;
import com.budgetmanagement.budgetmanagement.domain.user.UserReader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {
    private final UserReader userReader;
    private final BudgetManager budgetManager;
    private final CategoryAppender categoryAppender;

    public BudgetService(UserReader userReader, BudgetManager budgetManager, CategoryAppender categoryAppender) {
        this.userReader = userReader;
        this.budgetManager = budgetManager;
        this.categoryAppender = categoryAppender;
    }

    public void config(Long userId, BudgetAmount amount, List<CategoryBudget> categories) {
        User user = userReader.readBy(userId);

        Budget budget = budgetManager.init(user, amount);

        categoryAppender.append(budget, categories);
    }
}
