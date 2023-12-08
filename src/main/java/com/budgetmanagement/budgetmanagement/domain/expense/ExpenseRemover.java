package com.budgetmanagement.budgetmanagement.domain.expense;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ExpenseRemover {
    private final ExpenseRepository expenseRepository;

    public ExpenseRemover(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Transactional
    public void remove(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }
}
