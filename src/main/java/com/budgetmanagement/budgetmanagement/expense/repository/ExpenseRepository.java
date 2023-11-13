package com.budgetmanagement.budgetmanagement.expense.repository;

import com.budgetmanagement.budgetmanagement.expense.domain.Expense;
import com.budgetmanagement.budgetmanagement.expense.exception.ExpenseNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    default Expense getById(Long id) {
        return findById(id)
                .orElseThrow(ExpenseNotFoundException::new);
    }
}
