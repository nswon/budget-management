package com.budgetmanagement.budgetmanagement.budget.dto.request;

import jakarta.validation.constraints.Min;

public record BudgetTotalAmountRequest(
        @Min(value = 0, message = "최소 금액은 0원입니다.")
        int totalAmount
) {
}
