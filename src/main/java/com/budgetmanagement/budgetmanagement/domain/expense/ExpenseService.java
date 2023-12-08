package com.budgetmanagement.budgetmanagement.domain.expense;

import com.budgetmanagement.budgetmanagement.domain.user.User;
import com.budgetmanagement.budgetmanagement.domain.user.UserReader;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    private final UserReader userReader;
    private final ExpenseAppender expenseAppender;
    private final ExpenseReader expenseReader;
    private final ExpenseUpdater expenseUpdater;
    private final ExpenseRemover expenseRemover;
    private final ExpenseTotalExcluder expenseTotalExcluder;

    public ExpenseService(UserReader userReader, ExpenseAppender expenseAppender, ExpenseReader expenseReader,
                          ExpenseUpdater expenseUpdater, ExpenseRemover expenseRemover, ExpenseTotalExcluder expenseTotalExcluder) {
        this.userReader = userReader;
        this.expenseAppender = expenseAppender;
        this.expenseReader = expenseReader;
        this.expenseUpdater = expenseUpdater;
        this.expenseRemover = expenseRemover;
        this.expenseTotalExcluder = expenseTotalExcluder;
    }

    public void create(Long userId, ExpenseRecord record) {
        User user = userReader.readBy(userId);

        expenseAppender.append(user, record);
    }

    public void update(Long expenseId, ExpenseRecord record) {
        Expense expense = expenseReader.readBy(expenseId);

        expenseUpdater.update(expense, record);
    }

    public ExpenseResult getList(Long userId, String category, ExpenseRange range) {
        return expenseReader.read(userId, category, range);
    }

    public ExpenseContent get(Long expenseId) {
        Expense expense = expenseReader.readBy(expenseId);
        return new ExpenseContent(expense);
    }

    public void remove(Long expenseId) {
        expenseRemover.remove(expenseId);
    }

    public void exclude(Long expenseId) {
        expenseTotalExcluder.exclude(expenseId);
    }
}
