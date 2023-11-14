package com.budgetmanagement.budgetmanagement.budget.domain;

import com.budgetmanagement.budgetmanagement.budget.exception.BudgetNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum BudgetCategory {
    FOOD("식비"),
    TRANSPORTATION("교통"),
    FINANCE("금융"),
    SHOPPING("쇼핑"),
    LIFE("생활"),
    RESIDENCE("주거/통신"),
    HEALTHCARE("의료/건강"),
    LEISURE("여가");

    private final String name;

    public static List<BudgetCategory> getCategories() {
        return List.of(values());
    }

    public static BudgetCategory toEnum(String target) {
        return Arrays.stream(values())
                .filter(category -> category.isSameCategory(target))
                .findFirst()
                .orElseThrow(BudgetNotFoundException::new);
    }

    private boolean isSameCategory(String category) {
        return this.name.equals(category);
    }
}
