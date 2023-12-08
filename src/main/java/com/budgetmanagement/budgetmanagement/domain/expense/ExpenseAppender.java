package com.budgetmanagement.budgetmanagement.domain.expense;

import com.budgetmanagement.budgetmanagement.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ExpenseAppender {
    private final ExpenseRepository expenseRepository;

    public ExpenseAppender(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Transactional
    public void append(User user, ExpenseRecord record) {
        expenseRepository.save(newExpense(user, record));
    }

    private Expense newExpense(User user, ExpenseRecord record) {
        return Expense.builder()
                .user(user)
                .date(record.getDate())
                .amount(record.getAmount())
                .category(record.getCategory())
                .memo(record.getMemo())
                .build();
    }
}
