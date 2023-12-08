package com.budgetmanagement.budgetmanagement.domain.user;

import com.budgetmanagement.budgetmanagement.domain.budget.Budget;
import com.budgetmanagement.budgetmanagement.domain.expense.Expense;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
    private List<Budget> budgets = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private final List<Expense> expenses = new ArrayList<>();

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public void expend(Expense expense) {
        this.expenses.add(expense);
    }
}
