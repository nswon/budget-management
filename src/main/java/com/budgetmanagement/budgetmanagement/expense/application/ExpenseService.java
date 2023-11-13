package com.budgetmanagement.budgetmanagement.expense.application;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategoryType;
import com.budgetmanagement.budgetmanagement.expense.domain.Expense;
import com.budgetmanagement.budgetmanagement.expense.dto.request.ExpenseCreateRequest;
import com.budgetmanagement.budgetmanagement.expense.dto.request.ExpenseUpdateRequest;
import com.budgetmanagement.budgetmanagement.expense.dto.response.ExpenseCategoryTotalAmountResponse;
import com.budgetmanagement.budgetmanagement.expense.dto.response.ExpenseDetailResponse;
import com.budgetmanagement.budgetmanagement.expense.dto.response.ExpensesResponse;
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

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseService {
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseQRepository expenseQRepository;

    public void createExpense(Long userId, ExpenseCreateRequest request) {
        User user = userRepository.getById(userId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.parse(request.date(), formatter);

        Expense expense = Expense.builder()
                .user(user)
                .date(time)
                .amount(request.amount())
                .category(BudgetCategoryType.toEnum(request.category()))
                .memo(request.memo())
                .build();

        expenseRepository.save(expense);
    }

    public void updateExpense(Long expenseId, ExpenseUpdateRequest request) {
        Expense expense = expenseRepository.getById(expenseId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.parse(request.date(), formatter);

        expense.update(time, request.amount(), BudgetCategoryType.toEnum(request.category()), request.memo());
    }

    @Transactional(readOnly = true)
    public ExpensesResponse getExpenses(Long userId, String category, int minAmount, int maxAmount) {
        int totalAmount = expenseQRepository.getTotalAmount(userId, category, minAmount, maxAmount);
        List<ExpenseCategoryTotalAmountResponse> categoryTotalAmounts = expenseQRepository.getCategoryTotalAmounts(userId, category, minAmount, maxAmount);

        return expenseQRepository.getExpenses(userId, category, minAmount, maxAmount).stream()
                .collect(collectingAndThen(toList(), expenses -> new ExpensesResponse(totalAmount, categoryTotalAmounts, expenses)));
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
}
