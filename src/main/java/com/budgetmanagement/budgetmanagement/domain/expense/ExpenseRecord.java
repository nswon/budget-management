package com.budgetmanagement.budgetmanagement.domain.expense;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ExpenseRecord {
    private LocalDateTime date;
    private int amount;
    private String category;
    private String memo;

    public ExpenseRecord(String date, int amount, String category, String memo) {
        this.date = convert(date);
        this.amount = amount;
        this.category = category;
        this.memo = memo;
    }

    private LocalDateTime convert(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }
}
