package com.budgetmanagement.budgetmanagement.domain.expense;

import java.time.LocalDateTime;

public record ExpenseSummary(
        LocalDateTime date,
        int amount,
        String category
) {
}
