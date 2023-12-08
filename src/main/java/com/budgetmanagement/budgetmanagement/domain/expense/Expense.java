package com.budgetmanagement.budgetmanagement.domain.expense;

import com.budgetmanagement.budgetmanagement.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private String category;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Column(nullable = false)
    private boolean isExcluded = false; //합계 제외 여부

    @Builder
    public Expense(User user, LocalDateTime date, String category, int amount, String memo) {
        this.user = user;
        user.expend(this);
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.memo = memo;
    }

    public void exclude() {
        isExcluded = true;
    }

    public void update(LocalDateTime date, int amount, String category, String memo) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.memo = memo;
    }
}
