package com.budgetmanagement.budgetmanagement.domain.category;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryReader {

    public List<CategoryContent> read() {
        return DefaultCategory.getList().stream()
                .map(each -> new CategoryContent(each.getName()))
                .toList();
    }
}
