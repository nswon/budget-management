package com.budgetmanagement.budgetmanagement.controller.budget;

import com.budgetmanagement.budgetmanagement.controller.auth.AuthenticationPrincipal;
import com.budgetmanagement.budgetmanagement.controller.auth.LoginUser;
import com.budgetmanagement.budgetmanagement.controller.budget.request.BudgetConfigRequest;
import com.budgetmanagement.budgetmanagement.domain.budget.BudgetService;
import com.budgetmanagement.budgetmanagement.support.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/budgets")
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ApiResponse<Void> configBudget(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody @Valid BudgetConfigRequest request
    ) {
        budgetService.config(loginUser.id(), request.toAmount(), request.toCategoryBudget());
        return ApiResponse.success();
    }
}
