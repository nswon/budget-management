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
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BudgetCategory category;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private int ratio;

    @Builder
    public Budget(User user, BudgetCategory category, int amount, int ratio) {
        this.user = user;
        user.createBudgetCategory(this);
        this.category = category;
        this.amount = amount;
        this.ratio = ratio;
    }
}
