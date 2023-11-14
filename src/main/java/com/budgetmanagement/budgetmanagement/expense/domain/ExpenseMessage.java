package com.budgetmanagement.budgetmanagement.expense.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExpenseMessage {

    BAD("예산을 초과했어요! 아껴야 해요!"),
    DANGEROUS("기준을 넘었어요! 조금씩 아껴 써야 할 때가 온 것 같아요."),
    GOOD("절약을 잘 실천하고 계세요! 오늘도 절약 도전!");

    private final String message;
}
