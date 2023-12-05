package com.budgetmanagement.budgetmanagement.expense.controller;

import com.budgetmanagement.budgetmanagement.auth.api.AuthenticationPrincipal;
import com.budgetmanagement.budgetmanagement.auth.domain.LoginUser;
import com.budgetmanagement.budgetmanagement.expense.application.ExpenseService;
import com.budgetmanagement.budgetmanagement.expense.dto.request.ExpenseCreateRequest;
import com.budgetmanagement.budgetmanagement.expense.dto.request.ExpenseUpdateRequest;
import com.budgetmanagement.budgetmanagement.expense.dto.response.ExpenseDetailResponse;
import com.budgetmanagement.budgetmanagement.expense.dto.response.ExpenseRecommendResponse;
import com.budgetmanagement.budgetmanagement.expense.dto.response.ExpensesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Void> createExpense(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody ExpenseCreateRequest expenseCreateRequest
    ) {
        expenseService.createExpense(loginUser.id(), expenseCreateRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateExpense(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("id") Long expenseId,
            @RequestBody ExpenseUpdateRequest expenseUpdateRequest
    ) {
        expenseService.updateExpense(expenseId, expenseUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ExpensesResponse> getExpenses(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "min-amount", defaultValue = "0") int minAmount,
            @RequestParam(value = "max-amount", defaultValue = "0") int maxAmount
    ) {
        ExpensesResponse response = expenseService.getExpenses(loginUser.id(), category, minAmount, maxAmount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDetailResponse> getExpense(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("id") Long expenseId
    ) {
        ExpenseDetailResponse response = expenseService.getExpense(expenseId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("id") Long expenseId
    ) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> excludeExpense(
            @AuthenticationPrincipal LoginUser loginUser,
            @PathVariable("id") Long expenseId
    ) {
        expenseService.excludeExpense(expenseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/today/recommendation")
    public ResponseEntity<ExpenseRecommendResponse> recommendExpense(
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        ExpenseRecommendResponse response = expenseService.recommendExpense(loginUser.id());
        return ResponseEntity.ok(response);
    }
}
