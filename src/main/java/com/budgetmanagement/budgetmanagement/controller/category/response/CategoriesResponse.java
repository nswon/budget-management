package com.budgetmanagement.budgetmanagement.controller.category.response;

import com.budgetmanagement.budgetmanagement.domain.category.CategoryContent;

import java.util.List;

public record CategoriesResponse(List<CategoryResponse> categories) {

    public static CategoriesResponse of(List<CategoryContent> categories) {
        List<CategoryResponse> responses = categories.stream()
                .map(each -> new CategoryResponse(each.name()))
                .toList();

        return new CategoriesResponse(responses);
    }
}
