package com.budgetmanagement.budgetmanagement.user.domain;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategory;
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
    private final List<BudgetCategory> budgetCategories = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private final List<Expense> expenses = new ArrayList<>();

    @Builder
    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public void createBudgetCategory(BudgetCategory budgetCategory) {
        this.budgetCategories.add(budgetCategory);
    }

    public void expend(Expense expense) {
        this.expenses.add(expense);
    }
}
