package com.budgetmanagement.budgetmanagement.domain.category;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryReader categoryReader;

    public CategoryService(CategoryReader categoryReader) {
        this.categoryReader = categoryReader;
    }

    public List<CategoryContent> getList() {
        return categoryReader.read();
    }
}
