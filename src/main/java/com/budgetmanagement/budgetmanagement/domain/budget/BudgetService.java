package com.budgetmanagement.budgetmanagement.domain.budget;

import com.budgetmanagement.budgetmanagement.domain.user.User;
import com.budgetmanagement.budgetmanagement.domain.user.UserReader;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
public class BudgetService {
    private final UserReader userReader;
    private final BudgetManager budgetManager;
    private final BudgetAppender budgetAppender;
    private final BudgetRecommender budgetRecommender;

    public BudgetService(UserReader userReader, BudgetManager budgetManager, BudgetAppender budgetAppender, BudgetRecommender budgetRecommender) {
        this.userReader = userReader;
        this.budgetManager = budgetManager;
        this.budgetAppender = budgetAppender;
        this.budgetRecommender = budgetRecommender;
    }

    public void config(Long userId, BudgetAmount amount, BudgetRequest request) {
        User user = userReader.readBy(userId);

        budgetManager.init(user);

        budgetAppender.append(user, amount, request);
    }

    public List<BudgetContent> recommend(BudgetAmount amount) {
        return budgetRecommender.recommend(amount);
    }
}
