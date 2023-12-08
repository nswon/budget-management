package com.budgetmanagement.budgetmanagement.domain.expense;

import com.budgetmanagement.budgetmanagement.support.error.ApiException;
import com.budgetmanagement.budgetmanagement.support.error.ErrorType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    default Expense getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new ApiException(ErrorType.NOT_FOUND));
    }
}
