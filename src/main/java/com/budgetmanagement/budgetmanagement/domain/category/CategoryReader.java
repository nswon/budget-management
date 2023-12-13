package com.budgetmanagement.budgetmanagement.domain.category;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryReader {
    private final CategoryRepository categoryRepository;

    public CategoryReader(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryContent> read() {
        return categoryRepository.findAll().stream()
                .map(each -> new CategoryContent(each.getName()))
                .toList();
    }

    public Category readBy(String name) {
        return categoryRepository.getByName(name);
    }
}
