package com.budgetmanagement.budgetmanagement.domain.category;

import lombok.Getter;

import java.util.List;

@Getter
public enum DefaultCategory {
    FOOD("식비"),
    TRANSPORTATION("교통"),
    FINANCE("금융"),
    SHOPPING("쇼핑"),
    LIFE("생활"),
    RESIDENCE("주거/통신"),
    HEALTHCARE("의료/건강"),
    LEISURE("여가");

    private final String name;

    DefaultCategory(String name) {
        this.name = name;
    }

    public static List<DefaultCategory> getList() {
        return List.of(values());
    }
}
