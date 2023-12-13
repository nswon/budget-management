package com.budgetmanagement.budgetmanagement.domain.expense;

import com.budgetmanagement.budgetmanagement.domain.category.Category;
import com.budgetmanagement.budgetmanagement.domain.category.CategoryReader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ExpenseUpdater {
    private final CategoryReader categoryReader;

    public ExpenseUpdater(CategoryReader categoryReader) {
        this.categoryReader = categoryReader;
    }

    @Transactional
    public void update(Expense expense, ExpenseContent content) {
        Category category = updateCategory(expense.getCategory(), content.category());

        expense.update(category, content.date(), content.amount(), content.memo(), content.isExcluded());
    }

    private Category updateCategory(Category category, String target) {
        if(category.getName().equals(target)) {
            return category;
        }

        return categoryReader.readBy(target);
    }
}
