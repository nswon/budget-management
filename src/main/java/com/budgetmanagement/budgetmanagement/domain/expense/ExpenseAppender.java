package com.budgetmanagement.budgetmanagement.domain.expense;

import com.budgetmanagement.budgetmanagement.domain.category.Category;
import com.budgetmanagement.budgetmanagement.domain.category.CategoryReader;
import com.budgetmanagement.budgetmanagement.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ExpenseAppender {
    private final CategoryReader categoryReader;
    private final ExpenseRepository expenseRepository;

    public ExpenseAppender(CategoryReader categoryReader, ExpenseRepository expenseRepository) {
        this.categoryReader = categoryReader;
        this.expenseRepository = expenseRepository;
    }

    @Transactional
    public void append(User user, ExpenseContent content) {
        expenseRepository.save(newExpense(user, content));
    }

    private Expense newExpense(User user, ExpenseContent content) {
        Category category = categoryReader.readBy(content.category());
        return new Expense(user, category, content.date(), content.amount(), content.memo(), content.isExcluded());
    }
}
