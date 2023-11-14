package com.budgetmanagement.budgetmanagement.budget.application;

import com.budgetmanagement.budgetmanagement.budget.domain.Budget;
import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategory;
import com.budgetmanagement.budgetmanagement.budget.dto.request.BudgetCreateRequest;
import com.budgetmanagement.budgetmanagement.budget.dto.request.BudgetTotalAmountRequest;
import com.budgetmanagement.budgetmanagement.budget.dto.request.BudgetUpdateRequest;
import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetCategoryResponse;
import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetRecommendResponse;
import com.budgetmanagement.budgetmanagement.budget.repository.BudgetQRepository;
import com.budgetmanagement.budgetmanagement.budget.repository.BudgetRepository;
import com.budgetmanagement.budgetmanagement.user.domain.User;
import com.budgetmanagement.budgetmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BudgetService {
    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;
    private final BudgetQRepository budgetQRepository;

    @Transactional(readOnly = true)
    public List<BudgetCategoryResponse> getBudgetCategories() {
        return BudgetCategory.getCategories().stream()
                .map(BudgetCategoryResponse::new)
                .toList();
    }

    public void createBudget(Long userId, BudgetCreateRequest request) {
        User user = userRepository.getById(userId);

        List<Budget> categories = request.budgets().stream()
                .map(budgetByCategory -> Budget.builder()
                        .user(user)
                        .category(BudgetCategory.toEnum(budgetByCategory.category()))
                        .amount(budgetByCategory.amount())
                        .ratio(Math.toIntExact(Math.round((budgetByCategory.amount() / request.getTotalAmount()) * 100)))
                        .build())
                .toList();

        budgetRepository.saveAll(categories);
    }

    public void updateBudget(Long userId, BudgetUpdateRequest request) {
        budgetRepository.deleteAllByUserInBatch(userId);
        User user = userRepository.getById(userId);

        List<Budget> categories = request.budgets().stream()
                .map(budgetByCategory -> Budget.builder()
                        .user(user)
                        .category(BudgetCategory.toEnum(budgetByCategory.category()))
                        .amount(budgetByCategory.amount())
                        .ratio(Math.toIntExact(Math.round((budgetByCategory.amount() / request.getTotalAmount()) * 100)))
                        .build())
                .toList();

        budgetRepository.saveAll(categories);
    }

    @Transactional(readOnly = true)
    public List<BudgetRecommendResponse> getRecommendationBudget(BudgetTotalAmountRequest request) {
        int userCount = userRepository.findAll().size();
        return budgetQRepository.getRecommendationBudget(request.totalAmount(), userCount);
    }
}
