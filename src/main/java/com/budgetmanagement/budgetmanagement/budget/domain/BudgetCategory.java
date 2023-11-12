package com.budgetmanagement.budgetmanagement.budget.domain;

import com.budgetmanagement.budgetmanagement.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BudgetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type", nullable = false)
    private BudgetCategoryType categoryType;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private int ratio;

    @Builder
    public BudgetCategory(User user, BudgetCategoryType categoryType, int amount, int ratio) {
        this.user = user;
        user.createBudgetCategory(this);
        this.categoryType = categoryType;
        this.amount = amount;
        this.ratio = ratio;
    }
}
