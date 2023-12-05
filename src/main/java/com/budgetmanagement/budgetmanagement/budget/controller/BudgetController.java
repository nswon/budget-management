package com.budgetmanagement.budgetmanagement.budget.controller;

import com.budgetmanagement.budgetmanagement.auth.api.AuthenticationPrincipal;
import com.budgetmanagement.budgetmanagement.auth.domain.LoginUser;
import com.budgetmanagement.budgetmanagement.budget.application.BudgetService;
import com.budgetmanagement.budgetmanagement.budget.dto.request.BudgetUpdateRequest;
import com.budgetmanagement.budgetmanagement.budget.dto.request.BudgetCreateRequest;
import com.budgetmanagement.budgetmanagement.budget.dto.request.BudgetTotalAmountRequest;
import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetCategoryResponse;
import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetRecommendResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/budgets")
public class BudgetController {
    private final BudgetService budgetService;

    @GetMapping
    public ResponseEntity<List<BudgetCategoryResponse>> getBudgetCategories(
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        List<BudgetCategoryResponse> response = budgetService.getBudgetCategories();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> createBudget(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody @Valid BudgetCreateRequest budgetCreateRequest
    ) {
        budgetService.createBudget(loginUser.id(), budgetCreateRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateBudget(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody @Valid BudgetUpdateRequest budgetUpdateRequest
    ) {
        budgetService.updateBudget(loginUser.id(), budgetUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recommendation")
    public ResponseEntity<List<BudgetRecommendResponse>> getRecommendationBudget(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody @Valid BudgetTotalAmountRequest budgetTotalAmountRequest
    ) {
        List<BudgetRecommendResponse> response = budgetService.getRecommendationBudget(budgetTotalAmountRequest);
        return ResponseEntity.ok(response);
    }
}
