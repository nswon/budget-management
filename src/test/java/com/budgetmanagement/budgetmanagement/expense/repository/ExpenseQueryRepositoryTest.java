package com.budgetmanagement.budgetmanagement.expense.repository;

import com.budgetmanagement.budgetmanagement.domain.budget.category.BudgetCategoryType;
import com.budgetmanagement.budgetmanagement.domain.expense.Expense;
import com.budgetmanagement.budgetmanagement.controller.expense.response.ExpenseCategoryTotalResponse;
import com.budgetmanagement.budgetmanagement.controller.expense.response.ExpenseResponse;
import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseQueryRepository;
import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseRepository;
import com.budgetmanagement.budgetmanagement.domain.user.User;
import com.budgetmanagement.budgetmanagement.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
public class ExpenseQueryRepositoryTest {

    @Autowired
    private ExpenseQueryRepository expenseQueryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void saveUser() {
        User newUser = User.builder()
                .account("account")
                .password("password")
                .build();
        userRepository.save(newUser);
        user = newUser;
    }

    @Nested
    @DisplayName("총 합계를 구한다.")
    class getTotalAmount {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            Expense expense = Expense.builder()
                    .user(user)
                    .date(LocalDateTime.now())
                    .amount(1000)
                    .category(BudgetCategoryType.FOOD)
                    .memo("과자 사먹음")
                    .build();
            expenseRepository.save(expense);
            Expense expense2 = Expense.builder()
                    .user(user)
                    .date(LocalDateTime.now())
                    .amount(10000)
                    .category(BudgetCategoryType.HEALTHCARE)
                    .memo("병원 갔다옴")
                    .build();
            expenseRepository.save(expense2);

            //when
            int totalAmount = expenseQueryRepository.getTotalAmount(user.getId(), null, 0, 10000);

            //then
            assertThat(totalAmount).isEqualTo(11000);
        }

        @Test
        @DisplayName("성공: 합계제외 처리한 지출은 총 합계에서 제외된다.")
        void success2() {
            //given
            Expense expense = Expense.builder()
                    .user(user)
                    .date(LocalDateTime.now())
                    .amount(1000)
                    .category(BudgetCategoryType.FOOD)
                    .memo("과자 사먹음")
                    .build();
            expenseRepository.save(expense);
            Expense expense2 = Expense.builder()
                    .user(user)
                    .date(LocalDateTime.now())
                    .amount(10000)
                    .category(BudgetCategoryType.HEALTHCARE)
                    .memo("병원 갔다옴")
                    .build();
            expense2.excludeFromTotalAmount(); //합계에서 제외시키는 메서드
            expenseRepository.save(expense2);

            //when
            int totalAmount = expenseQueryRepository.getTotalAmount(user.getId(), null, 0, 10000);

            //then
            assertThat(totalAmount).isEqualTo(1000);
        }
    }

    @Test
    @DisplayName("카테고리 별 합계를 구한다.")
    void getCategoryTotalAmounts() {
        //given
        Expense expense = Expense.builder()
                .user(user)
                .date(LocalDateTime.now())
                .amount(1000)
                .category(BudgetCategoryType.FOOD)
                .memo("과자 사먹음")
                .build();
        expenseRepository.save(expense);
        Expense expense2 = Expense.builder()
                .user(user)
                .date(LocalDateTime.now())
                .amount(10000)
                .category(BudgetCategoryType.HEALTHCARE)
                .memo("병원 갔다옴")
                .build();
        expenseRepository.save(expense2);

        //when
        List<ExpenseCategoryTotalResponse> response = expenseQueryRepository.getCategoryTotalAmounts(user.getId(), null, 0, 10000);

        //then
        int totalAmount = response.stream()
                .mapToInt(ExpenseCategoryTotalResponse::totalAmount)
                .sum();

        assertAll(() -> {
            assertThat(response).hasSize(2);
            assertThat(totalAmount).isEqualTo(11000);
        });
    }

    @Nested
    @DisplayName("목록을 가져온다.")
    class getExpenseResult {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            Expense expense = Expense.builder()
                    .user(user)
                    .date(LocalDateTime.now())
                    .amount(1000)
                    .category(BudgetCategoryType.FOOD)
                    .memo("과자 사먹음")
                    .build();
            expenseRepository.save(expense);
            Expense expense2 = Expense.builder()
                    .user(user)
                    .date(LocalDateTime.now())
                    .amount(10000)
                    .category(BudgetCategoryType.HEALTHCARE)
                    .memo("병원 갔다옴")
                    .build();
            expenseRepository.save(expense2);

            //when
            List<ExpenseResponse> response = expenseQueryRepository.getExpenses(user.getId(), null, 0, 10000);

            //then
            assertThat(response).hasSize(2);
        }

        @Test
        @DisplayName("성공: 최신순으로 정렬한다.")
        void success2() {
            //given
            Expense expense = Expense.builder()
                    .user(user)
                    .date(LocalDateTime.of(2023, 11, 13, 10, 15))
                    .amount(1000)
                    .category(BudgetCategoryType.FOOD)
                    .memo("과자 사먹음")
                    .build();
            expenseRepository.save(expense);
            Expense expense2 = Expense.builder()
                    .user(user)
                    .date(LocalDateTime.of(2023, 12, 13, 10, 15))
                    .amount(10000)
                    .category(BudgetCategoryType.HEALTHCARE)
                    .memo("병원 갔다옴")
                    .build();
            expenseRepository.save(expense2);

            //when
            List<ExpenseResponse> response = expenseQueryRepository.getExpenses(user.getId(), null, 0, 10000);

            //then
            assertThat(response.get(0).category()).isEqualTo("의료/건강"); //가장 최신 지출이 마지막으로 작성한 지출인지 체크
        }

        @Test
        @DisplayName("성공: 특정 카테고리만 가져온다.")
        void success3() {
            //given
            Expense expense = Expense.builder()
                    .user(user)
                    .date(LocalDateTime.now())
                    .amount(1000)
                    .category(BudgetCategoryType.FOOD)
                    .memo("과자 사먹음")
                    .build();
            expenseRepository.save(expense);
            Expense expense2 = Expense.builder()
                    .user(user)
                    .date(LocalDateTime.now())
                    .amount(5000)
                    .category(BudgetCategoryType.FOOD)
                    .memo("이번엔 밥 먹음")
                    .build();
            expenseRepository.save(expense2);
            Expense expense3 = Expense.builder()
                    .user(user)
                    .date(LocalDateTime.now())
                    .amount(10000)
                    .category(BudgetCategoryType.HEALTHCARE)
                    .memo("병원 갔다옴")
                    .build();
            expenseRepository.save(expense3);

            //when
            List<ExpenseResponse> response = expenseQueryRepository.getExpenses(user.getId(), "식비", 0, 10000);

            //then
            assertThat(response).hasSize(2);
        }

        @Test
        @DisplayName("성공: 최소 금액과 최대 금액 사이의 지출을 가져온다.")
        void success4() {
            //given
            Expense expense = Expense.builder()
                    .user(user)
                    .date(LocalDateTime.now())
                    .amount(1000)
                    .category(BudgetCategoryType.FOOD)
                    .memo("과자 사먹음")
                    .build();
            expenseRepository.save(expense);
            Expense expense2 = Expense.builder()
                    .user(user)
                    .date(LocalDateTime.now())
                    .amount(5000)
                    .category(BudgetCategoryType.FOOD)
                    .memo("이번엔 밥 먹음")
                    .build();
            expenseRepository.save(expense2);

            //when
            int minAmount = 0;
            int maxAmount = 1000; //첫번째 지출만 가져오도록 설정
            List<ExpenseResponse> response = expenseQueryRepository.getExpenses(user.getId(), null, minAmount, maxAmount);

            //then
            assertThat(response).hasSize(1);
        }

        @Test
        @DisplayName("성공: 요청된 최대 금액이 최소 금액보다 작을 경우 빈값 반환")
        void success5() {
            //given
            Expense expense = Expense.builder()
                    .user(user)
                    .date(LocalDateTime.now())
                    .amount(1000)
                    .category(BudgetCategoryType.FOOD)
                    .memo("과자 사먹음")
                    .build();
            expenseRepository.save(expense);
            Expense expense2 = Expense.builder()
                    .user(user)
                    .date(LocalDateTime.now())
                    .amount(5000)
                    .category(BudgetCategoryType.FOOD)
                    .memo("이번엔 밥 먹음")
                    .build();
            expenseRepository.save(expense2);

            //when
            int minAmount = 1000;
            int maxAmount = 800; //최소 금액보다 작게 요청
            List<ExpenseResponse> response = expenseQueryRepository.getExpenses(user.getId(), null, minAmount, maxAmount);

            //then
            assertThat(response).isEmpty();
        }
    }
}
