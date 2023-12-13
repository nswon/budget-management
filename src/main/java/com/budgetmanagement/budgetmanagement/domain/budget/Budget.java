package com.budgetmanagement.budgetmanagement.domain.budget;

import com.budgetmanagement.budgetmanagement.domain.category.Category;
import com.budgetmanagement.budgetmanagement.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private double ratio;

    @Column(nullable = false)
    private YearMonth date;

    public Budget(User user, Category category, int amount, double ratio, YearMonth date) {
        this.user = user;
        this.category = category;
        this.amount = amount;
        this.ratio = ratio;
        this.date = date;
    }
}
