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
    private final ExpenseExcluder expenseExcluder;

    public ExpenseService(UserReader userReader, ExpenseAppender expenseAppender, ExpenseReader expenseReader,
                          ExpenseUpdater expenseUpdater, ExpenseRemover expenseRemover, ExpenseExcluder expenseExcluder) {
        this.userReader = userReader;
        this.expenseAppender = expenseAppender;
        this.expenseReader = expenseReader;
        this.expenseUpdater = expenseUpdater;
        this.expenseRemover = expenseRemover;
        this.expenseExcluder = expenseExcluder;
    }

    public void create(Long userId, ExpenseContent content) {
        User user = userReader.readBy(userId);

        expenseAppender.append(user, content);
    }

    public void update(long expenseId, ExpenseContent content) {
        Expense expense = expenseReader.readBy(expenseId);

        expenseUpdater.update(expense, content);
    }

    public ExpenseReadResult getList(Long userId, String category, ExpenseRange range) {
        return expenseReader.read(userId, category, range);
    }

    public ExpenseContent get(long expenseId) {
        Expense expense = expenseReader.readBy(expenseId);
        return new ExpenseContent(expense);
    }

    public void remove(long expenseId) {
        expenseRemover.remove(expenseId);
    }

    public void exclude(long expenseId) {
        expenseExcluder.exclude(expenseId);
    }
}
