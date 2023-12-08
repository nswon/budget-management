package com.budgetmanagement.budgetmanagement.domain.budget;

import com.budgetmanagement.budgetmanagement.domain.category.Category;
import com.budgetmanagement.budgetmanagement.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private YearMonth month;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "budget")
    private List<Category> categories = new ArrayList<>();

    public Budget(User user, int amount, YearMonth month) {
        this.user = user;
        this.amount = amount;
        this.month = month;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }
}
