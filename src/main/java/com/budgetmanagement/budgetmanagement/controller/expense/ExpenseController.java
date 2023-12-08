package com.budgetmanagement.budgetmanagement.controller.expense;

import com.budgetmanagement.budgetmanagement.controller.auth.AuthenticationPrincipal;
import com.budgetmanagement.budgetmanagement.controller.auth.LoginUser;
import com.budgetmanagement.budgetmanagement.controller.expense.request.ExpenseCreateRequest;
import com.budgetmanagement.budgetmanagement.controller.expense.request.ExpenseUpdateRequest;
import com.budgetmanagement.budgetmanagement.controller.expense.response.ExpenseDetailResponse;
import com.budgetmanagement.budgetmanagement.controller.expense.response.ExpensesResponse;
import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseContent;
import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseRange;
import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseResult;
import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseService;
import com.budgetmanagement.budgetmanagement.support.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ApiResponse<Void> createExpense(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody ExpenseCreateRequest request
    ) {
        expenseService.create(loginUser.id(), request.toRecord());
        return ApiResponse.success();
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateExpense(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("id") Long expenseId,
            @RequestBody ExpenseUpdateRequest request
    ) {
        expenseService.update(expenseId, request.toRecord());
        return ApiResponse.success();
    }

    @GetMapping
    public ApiResponse<ExpensesResponse> getExpenses(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int minAmount,
            @RequestParam(defaultValue = "0") int maxAmount
    ) {
         ExpenseResult result = expenseService.getList(loginUser.id(), category, new ExpenseRange(minAmount, maxAmount));
        return ApiResponse.success(ExpensesResponse.of(result));
    }

    @GetMapping("/{id}")
    public ApiResponse<ExpenseDetailResponse> getExpense(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("id") Long expenseId
    ) {
        ExpenseContent content = expenseService.get(expenseId);
        return ApiResponse.success(new ExpenseDetailResponse(content));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> removeExpense(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("id") Long expenseId
    ) {
        expenseService.remove(expenseId);
        return ApiResponse.success();
    }

    @PatchMapping("/{id}")
    public ApiResponse<Void> excludeExpense(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("id") Long expenseId
    ) {
        expenseService.exclude(expenseId);
        return ApiResponse.success();
    }
}
