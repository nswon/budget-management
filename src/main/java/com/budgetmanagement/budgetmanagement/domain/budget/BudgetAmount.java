package com.budgetmanagement.budgetmanagement.domain.budget;

public record BudgetAmount(
        int amount
) {
    public double calculateRatio(int amount) {
        return amount / (double) this.amount * 100;
    }
}
