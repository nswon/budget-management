package com.budgetmanagement.budgetmanagement.user.domain;

import com.budgetmanagement.budgetmanagement.budget.domain.Budget;
import com.budgetmanagement.budgetmanagement.expense.domain.Expense;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private final List<Budget> budgets = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private final List<Expense> expenses = new ArrayList<>();

    @Builder
    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public void createBudgetCategory(Budget budget) {
        this.budgets.add(budget);
    }

    public void expend(Expense expense) {
        this.expenses.add(expense);
    }

    public int getRemainingBudget() {
        int totalBudgetAmount = getTotalBudgetAmount();
        int totalExpenseAmount = getTotalExpenseAmount();
        return totalBudgetAmount - totalExpenseAmount;
    }

    private int getTotalBudgetAmount() {
        return this.budgets.stream()
                .mapToInt(Budget::getAmount)
                .sum();
    }

    private int getTotalExpenseAmount() {
        return this.expenses.stream()
                .mapToInt(Expense::getAmount)
                .sum();
    }
}
