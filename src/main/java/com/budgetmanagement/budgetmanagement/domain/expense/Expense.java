package com.budgetmanagement.budgetmanagement.domain.expense;

import com.budgetmanagement.budgetmanagement.domain.category.Category;
import com.budgetmanagement.budgetmanagement.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expense {

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
    private LocalDateTime date;

    @Column(nullable = false)
    private int amount;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Column(nullable = false)
    private boolean isExcluded; //합계 제외 여부

    public Expense(User user, Category category, LocalDateTime date, int amount, String memo, boolean isExcluded) {
        this.user = user;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.memo = memo;
        this.isExcluded = isExcluded;
    }

    public void update(Category category, LocalDateTime date, int amount, String memo, boolean isExcluded) {
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.memo = memo;
        this.isExcluded = isExcluded;
    }

    public void exclude() {
        isExcluded = true;
    }
}
