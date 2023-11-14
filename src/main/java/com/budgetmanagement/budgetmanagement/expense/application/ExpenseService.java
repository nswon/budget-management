package com.budgetmanagement.budgetmanagement.expense.application;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategory;
import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetCategoryAmountResponse;
import com.budgetmanagement.budgetmanagement.budget.repository.BudgetQRepository;
import com.budgetmanagement.budgetmanagement.expense.domain.Expense;
import com.budgetmanagement.budgetmanagement.expense.dto.ExpenseAndMessage;
import com.budgetmanagement.budgetmanagement.expense.dto.request.ExpenseCreateRequest;
import com.budgetmanagement.budgetmanagement.expense.dto.request.ExpenseUpdateRequest;
import com.budgetmanagement.budgetmanagement.expense.dto.response.*;
import com.budgetmanagement.budgetmanagement.expense.repository.ExpenseQRepository;
import com.budgetmanagement.budgetmanagement.expense.repository.ExpenseRepository;
import com.budgetmanagement.budgetmanagement.user.domain.User;
import com.budgetmanagement.budgetmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseService {
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseQRepository expenseQRepository;
    private final ExpenseAndMessageRecommender expenseAndMessageRecommender;
    private final BudgetQRepository budgetQRepository;

    public void createExpense(Long userId, ExpenseCreateRequest request) {
        User user = userRepository.getById(userId);

        LocalDateTime time = toLocalDateTime(request.date());
        Expense expense = Expense.builder()
                .user(user)
                .date(time)
                .amount(request.amount())
                .category(BudgetCategory.toEnum(request.category()))
                .memo(request.memo())
                .build();

        expenseRepository.save(expense);
    }

    public void updateExpense(Long expenseId, ExpenseUpdateRequest request) {
        Expense expense = expenseRepository.getById(expenseId);

        LocalDateTime time = toLocalDateTime(request.date());
        expense.update(time, request.amount(), BudgetCategory.toEnum(request.category()), request.memo());
    }

    private LocalDateTime toLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }

    @Transactional(readOnly = true)
    public ExpensesResponse getExpenses(Long userId, String category, int minAmount, int maxAmount) {
        int totalAmount = expenseQRepository.getTotalAmount(userId, category, minAmount, maxAmount);
        List<ExpenseCategoryTotalAmountResponse> categoryTotalAmounts = expenseQRepository.getCategoryTotalAmounts(userId, category, minAmount, maxAmount);

        List<ExpenseResponse> expenses = expenseQRepository.getExpenses(userId, category, minAmount, maxAmount);
        return new ExpensesResponse(totalAmount, categoryTotalAmounts, expenses);
    }

    @Transactional(readOnly = true)
    public ExpenseDetailResponse getExpense(Long expenseId) {
        Expense expense = expenseRepository.getById(expenseId);
        return new ExpenseDetailResponse(expense);
    }

    public void deleteExpense(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }

    public void excludeExpense(Long expenseId) {
        Expense expense = expenseRepository.getById(expenseId);
        expense.excludeFromTotalAmount();
    }

    @Transactional(readOnly = true)
    public ExpenseRecommendResponse recommendExpense(Long userId) {
        User user = userRepository.getById(userId);

        int remainingBudget = user.getRemainingBudget();
        ExpenseAndMessage expenseAndMessage = expenseAndMessageRecommender.recommend(remainingBudget);
        List<BudgetCategoryAmountResponse> categoryAmounts = budgetQRepository.getCategoryAmounts(expenseAndMessage.expense(), user);

        return new ExpenseRecommendResponse(expenseAndMessage.expense(), categoryAmounts, expenseAndMessage.message());

    }
}
