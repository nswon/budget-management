package com.budgetmanagement.budgetmanagement.controller.expense;

import com.budgetmanagement.budgetmanagement.controller.auth.AuthenticationPrincipal;
import com.budgetmanagement.budgetmanagement.controller.auth.LoginUser;
import com.budgetmanagement.budgetmanagement.controller.expense.request.ExpenseCreateRequest;
import com.budgetmanagement.budgetmanagement.controller.expense.request.ExpenseUpdateRequest;
import com.budgetmanagement.budgetmanagement.controller.expense.response.ExpenseDetailResponse;
import com.budgetmanagement.budgetmanagement.controller.expense.response.ExpensesResponse;
import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseContent;
import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseRange;
import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseReadResult;
import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseService;
import com.budgetmanagement.budgetmanagement.support.response.ApiResponse;
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
            @AuthenticationPrincipal LoginUser user,
            @RequestBody ExpenseCreateRequest request
    ) {
        expenseService.create(user.id(), request.toContent());
        return ApiResponse.success();
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateExpense(
            @AuthenticationPrincipal LoginUser user,
            @PathVariable long id,
            @RequestBody ExpenseUpdateRequest request
    ) {
        expenseService.update(id, request.toContent());
        return ApiResponse.success();
    }

    @GetMapping
    public ApiResponse<ExpensesResponse> getExpenses(
            @AuthenticationPrincipal LoginUser user,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int minAmount,
            @RequestParam(defaultValue = "0") int maxAmount
    ) {
         ExpenseReadResult result = expenseService.getList(user.id(), category, new ExpenseRange(minAmount, maxAmount));
        return ApiResponse.success(ExpensesResponse.of(result));
    }

    @GetMapping("/{id}")
    public ApiResponse<ExpenseDetailResponse> getExpense(
            @AuthenticationPrincipal LoginUser user,
            @PathVariable long id
    ) {
        ExpenseContent content = expenseService.get(id);
        return ApiResponse.success(new ExpenseDetailResponse(content));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> removeExpense(
            @AuthenticationPrincipal LoginUser user,
            @PathVariable long id
    ) {
        expenseService.remove(id);
        return ApiResponse.success();
    }

    @PatchMapping("/{id}")
    public ApiResponse<Void> excludeExpense(
            @AuthenticationPrincipal LoginUser user,
            @PathVariable long id
    ) {
        expenseService.exclude(id);
        return ApiResponse.success();
    }
}
