package com.budgetmanagement.budgetmanagement.budget.application;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategory;
import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategoryType;
import com.budgetmanagement.budgetmanagement.budget.dto.request.BudgetCreateRequest;
import com.budgetmanagement.budgetmanagement.budget.dto.request.BudgetTotalAmountRequest;
import com.budgetmanagement.budgetmanagement.budget.dto.request.BudgetUpdateRequest;
import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetCategoryResponse;
import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetRecommendationResponse;
import com.budgetmanagement.budgetmanagement.budget.repository.BudgetCategoryQRepository;
import com.budgetmanagement.budgetmanagement.budget.repository.BudgetCategoryRepository;
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
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final BudgetCategoryQRepository budgetCategoryQRepository;

    @Transactional(readOnly = true)
    public List<BudgetCategoryResponse> getBudgetCategories() {
        return BudgetCategoryType.getCategories().stream()
                .map(BudgetCategoryResponse::new)
                .toList();
    }

    public void createBudget(Long userId, BudgetCreateRequest request) {
        User user = userRepository.getById(userId);

        List<BudgetCategory> categories = request.budgets().stream()
                .map(budgetByCategory -> BudgetCategory.builder()
                        .user(user)
                        .categoryType(BudgetCategoryType.toEnum(budgetByCategory.category()))
                        .amount(budgetByCategory.amount())
                        .ratio(Math.toIntExact(Math.round((budgetByCategory.amount() / request.getTotalAmount()) * 100)))
                        .build())
                .toList();

        budgetCategoryRepository.saveAll(categories);
    }

    public void updateBudget(Long userId, BudgetUpdateRequest request) {
        budgetCategoryRepository.deleteAllByUserInBatch(userId);
        User user = userRepository.getById(userId);

        List<BudgetCategory> categories = request.budgets().stream()
                .map(budgetByCategory -> BudgetCategory.builder()
                        .user(user)
                        .categoryType(BudgetCategoryType.toEnum(budgetByCategory.category()))
                        .amount(budgetByCategory.amount())
                        .ratio(Math.toIntExact(Math.round((budgetByCategory.amount() / request.getTotalAmount()) * 100)))
                        .build())
                .toList();

        budgetCategoryRepository.saveAll(categories);
    }

    @Transactional(readOnly = true)
    public List<BudgetRecommendationResponse> getRecommendationBudget(BudgetTotalAmountRequest request) {
        int userCount = userRepository.findAll().size();
        return budgetCategoryQRepository.getRecommendationBudget(request.totalAmount(), userCount);
    }
}
