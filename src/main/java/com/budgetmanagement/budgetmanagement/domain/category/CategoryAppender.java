package com.budgetmanagement.budgetmanagement.domain.category;

import com.budgetmanagement.budgetmanagement.domain.budget.Budget;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CategoryAppender {
    private final CategoryRepository categoryRepository;

    public CategoryAppender(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void append(Budget budget, List<CategoryBudget> target) {
        List<Category> categories = target.stream()
                .map(each -> new Category(budget, each.name(), each.amount()))
                .toList();

        categoryRepository.saveAll(categories);
    }
}
