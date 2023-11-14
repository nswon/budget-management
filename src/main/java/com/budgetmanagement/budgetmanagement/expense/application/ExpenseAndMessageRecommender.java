package com.budgetmanagement.budgetmanagement.expense.application;

import com.budgetmanagement.budgetmanagement.expense.domain.ExpenseMessage;
import com.budgetmanagement.budgetmanagement.expense.dto.ExpenseAndMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

@Component
public class ExpenseAndMessageRecommender {
    private static final int MIN_AMOUNT = 10000;

    //사용 가능한 지출과 유저의 상황에 맞는 1문장의 멘트 반환
    public ExpenseAndMessage recommend(int remainingBudget) {
        LocalDateTime now = LocalDateTime.now();
        int recommendationExpense = getRecommendationExpense(remainingBudget, now);

        //기간 전체 예산을 초과했을 때
        if(recommendationExpense < MIN_AMOUNT) {
            return new ExpenseAndMessage(MIN_AMOUNT,  ExpenseMessage.BAD.getMessage());
        }

//        //현재까지 지출된 금액이 기준을 넘었을 때
//        int dayLength = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth();
//        int defaultExpense = Math.toIntExact(Math.round(remainingBudget / (double) dayLength));
//        if(recommendationExpense < defaultExpense) {
//            return new ExpenseAndMessage(recommendationExpense, ExpenseMessage.DANGEROUS.getMessage());
//        }

        return new ExpenseAndMessage(recommendationExpense, ExpenseMessage.GOOD.getMessage());
    }

    private int getRecommendationExpense(int remainingBudget, LocalDateTime now) {
        int remainingDay = getRemainingDay(now);
        return Math.toIntExact(Math.round(remainingBudget / (double) remainingDay));
    }

    private int getRemainingDay(LocalDateTime now) {
        LocalDateTime nextMonth = now.plusMonths(1).withDayOfMonth(1);
        return Math.toIntExact(now.until(nextMonth, ChronoUnit.DAYS));
    }
}
