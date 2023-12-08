package com.budgetmanagement.budgetmanagement.domain.expense;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ExpenseUpdater {

    @Transactional
    public void update(Expense expense, ExpenseRecord record) {
        expense.update(record.getDate(), record.getAmount(), record.getCategory(), record.getMemo());
    }
}
