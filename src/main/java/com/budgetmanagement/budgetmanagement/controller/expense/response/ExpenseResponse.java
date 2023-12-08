package com.budgetmanagement.budgetmanagement.controller.expense.response;

import java.time.LocalDateTime;

public record ExpenseResponse(
        LocalDateTime date,
        int amount,
        String category
) {
}
