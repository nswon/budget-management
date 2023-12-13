package com.budgetmanagement.budgetmanagement.domain.budget;

import java.util.List;

public record BudgetRequest(
        List<BudgetContent> contents
) {
}
