package com.budgetmanagement.budgetmanagement.domain.category;

import com.budgetmanagement.budgetmanagement.domain.budget.Budget;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int amount;

    public Category(Budget budget, String name, int amount) {
        this.budget = budget;
        budget.addCategory(this);
        this.name = name;
        this.amount = amount;
    }
}
