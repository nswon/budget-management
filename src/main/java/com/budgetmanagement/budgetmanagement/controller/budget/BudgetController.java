package com.budgetmanagement.budgetmanagement.controller.budget;

import com.budgetmanagement.budgetmanagement.controller.auth.AuthenticationPrincipal;
import com.budgetmanagement.budgetmanagement.controller.auth.LoginUser;
import com.budgetmanagement.budgetmanagement.controller.budget.request.BudgetConfigRequest;
import com.budgetmanagement.budgetmanagement.controller.budget.response.BudgetsRecommendResponse;
import com.budgetmanagement.budgetmanagement.domain.budget.BudgetAmount;
import com.budgetmanagement.budgetmanagement.domain.budget.BudgetContent;
import com.budgetmanagement.budgetmanagement.domain.budget.BudgetService;
import com.budgetmanagement.budgetmanagement.support.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ApiResponse<Void> configBudget(
            @AuthenticationPrincipal LoginUser user,
            @RequestBody @Valid BudgetConfigRequest request
    ) {
        budgetService.config(user.id(), request.toRequest());
        return ApiResponse.success();
    }

    @GetMapping("/recommend")
    public ApiResponse<BudgetsRecommendResponse> recommendBudget(
            @AuthenticationPrincipal LoginUser user,
            @RequestParam int amount
    ) {
        List<BudgetContent> budgets = budgetService.recommend(new BudgetAmount(amount));
        return ApiResponse.success(BudgetsRecommendResponse.of(budgets));
    }
}
