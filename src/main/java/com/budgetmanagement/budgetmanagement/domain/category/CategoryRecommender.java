package com.budgetmanagement.budgetmanagement.domain.category;

import com.budgetmanagement.budgetmanagement.domain.budget.BudgetAmount;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryRecommender {
    private final CategoryQueryRepository categoryQueryRepository;

    public CategoryRecommender(CategoryQueryRepository categoryQueryRepository) {
        this.categoryQueryRepository = categoryQueryRepository;
    }

    public List<CategoryBudget> recommend(BudgetAmount amount) {
        return categoryQueryRepository.getList(amount);
    }
}
