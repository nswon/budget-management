package com.budgetmanagement.budgetmanagement.domain.category;

import com.budgetmanagement.budgetmanagement.domain.budget.BudgetAmount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryReader categoryReader;
    private final CategoryRecommender categoryRecommender;

    public CategoryService(CategoryReader categoryReader, CategoryRecommender categoryRecommender) {
        this.categoryReader = categoryReader;
        this.categoryRecommender = categoryRecommender;
    }

    public List<CategoryContent> getList() {
        return categoryReader.read();
    }

    public List<CategoryBudget> recommend(BudgetAmount amount) {
        return categoryRecommender.recommend(amount);
    }
}
