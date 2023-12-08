package com.budgetmanagement.budgetmanagement.controller.category;

import com.budgetmanagement.budgetmanagement.controller.auth.AuthenticationPrincipal;
import com.budgetmanagement.budgetmanagement.controller.auth.LoginUser;
import com.budgetmanagement.budgetmanagement.controller.category.request.BudgetAmountRequest;
import com.budgetmanagement.budgetmanagement.controller.category.response.CategoriesResponse;
import com.budgetmanagement.budgetmanagement.controller.category.response.RecommendCategoriesResponse;
import com.budgetmanagement.budgetmanagement.domain.category.CategoryBudget;
import com.budgetmanagement.budgetmanagement.domain.category.CategoryContent;
import com.budgetmanagement.budgetmanagement.domain.category.CategoryService;
import com.budgetmanagement.budgetmanagement.support.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ApiResponse<CategoriesResponse> getCategories(@AuthenticationPrincipal LoginUser user) {
        List<CategoryContent> categories = categoryService.getList();
        return ApiResponse.success(CategoriesResponse.of(categories));
    }

    @GetMapping("/recommend")
    public ApiResponse<RecommendCategoriesResponse> recommendCategories(
            @AuthenticationPrincipal LoginUser user,
            @RequestBody BudgetAmountRequest request
    ) {
        List<CategoryBudget> categories = categoryService.recommend(request.toAmount());
        return ApiResponse.success(RecommendCategoriesResponse.of(categories));
    }
}