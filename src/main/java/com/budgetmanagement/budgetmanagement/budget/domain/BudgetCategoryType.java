package com.budgetmanagement.budgetmanagement.budget.domain;

import com.budgetmanagement.budgetmanagement.budget.exception.BudgetCategoryNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum BudgetCategoryType {
    FOOD("식비"),
    TRANSPORTATION("교통"),
    FINANCE("금융"),
    SHOPPING("쇼핑"),
    LIFE("생활"),
    RESIDENCE("주거/통신"),
    HEALTHCARE("의료/건강"),
    LEISURE("여가");

    private final String name;

    public static List<BudgetCategoryType> getCategories() {
        return List.of(values());
    }

    public static BudgetCategoryType toEnum(String target) {
        return Arrays.stream(values())
                .filter(category -> category.isSameType(target))
                .findFirst()
                .orElseThrow(BudgetCategoryNotFoundException::new);
    }

    private boolean isSameType(String category) {
        return this.name.equals(category);
    }
}
