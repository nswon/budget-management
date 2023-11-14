package com.budgetmanagement.budgetmanagement.expense.application;

import com.budgetmanagement.budgetmanagement.expense.domain.ExpenseMessage;
import com.budgetmanagement.budgetmanagement.expense.dto.ExpenseAndMessage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class ExpenseAndMessageRecommenderTest {

    @Autowired
    private ExpenseAndMessageRecommender expenseAndMessageRecommender;

    @Nested
    @DisplayName("사용 가능한 지출과 유저의 상황에 맞는 1문장의 멘트를 반환한다.")
    class recommend {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            int remainingBudget = 1000000;

            //when
            ExpenseAndMessage expenseAndMessage = expenseAndMessageRecommender.recommend(remainingBudget);

            //then
            assertAll(() -> {
                assertThat(expenseAndMessage).isNotNull();
                assertThat(expenseAndMessage.message()).isEqualTo(ExpenseMessage.GOOD.getMessage());
            });
        }

        @Test
        @Disabled("알고리즘 다시 개선")
        @DisplayName("현재까지 지출된 금액이 기준을 넘었을 때 상황에 맞는 멘트를 반환한다.")
        void success2() {
            //given
            int remainingBudget = 300000;

            //when
            ExpenseAndMessage expenseAndMessage = expenseAndMessageRecommender.recommend(remainingBudget);

            //then
            assertThat(expenseAndMessage.message()).isEqualTo(ExpenseMessage.DANGEROUS.getMessage());
        }

        @Test
        @DisplayName("사용 가능한 지출이 최소 금액 이하이거나, 기간 전체 예산을 초과했을 때 상황에 멘트를 반환한다.")
        void success3() {
            //given
            int remainingBudget = 10000;

            //when
            ExpenseAndMessage expenseAndMessage = expenseAndMessageRecommender.recommend(remainingBudget);

            //then
            assertThat(expenseAndMessage.message()).isEqualTo(ExpenseMessage.BAD.getMessage());
        }
    }
}
