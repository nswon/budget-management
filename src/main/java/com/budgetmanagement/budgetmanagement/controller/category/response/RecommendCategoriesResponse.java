package com.budgetmanagement.budgetmanagement.controller.category.response;

import com.budgetmanagement.budgetmanagement.domain.category.CategoryBudget;

import java.util.List;

public record RecommendCategoriesResponse(List<RecommendCategoryResponse> categories) {

    public static RecommendCategoriesResponse of(List<CategoryBudget> categories) {
        List<RecommendCategoryResponse> responses = categories.stream()
                .map(each -> new RecommendCategoryResponse(each.name(), each.amount()))
                .toList();

        return new RecommendCategoriesResponse(responses);
    }
}
