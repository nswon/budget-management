package com.budgetmanagement.budgetmanagement.controller.expense.request;

import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseContent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ExpenseCreateRequest(
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime date,
        int amount,
        String category,
        String memo
) {
    public ExpenseContent toContent() {
        return new ExpenseContent(date, amount, category, memo);
    }
}
