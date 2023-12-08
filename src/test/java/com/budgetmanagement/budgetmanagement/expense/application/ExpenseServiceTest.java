package com.budgetmanagement.budgetmanagement.expense.application;

import com.budgetmanagement.budgetmanagement.domain.budget.Budget;
import com.budgetmanagement.budgetmanagement.domain.budget.category.BudgetCategoryType;
import com.budgetmanagement.budgetmanagement.controller.budget.BudgetCategoryAmountResponse;
import com.budgetmanagement.budgetmanagement.domain.budget.BudgetRepository;
import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseService;
import com.budgetmanagement.budgetmanagement.domain.expense.Expense;
import com.budgetmanagement.budgetmanagement.controller.expense.request.ExpenseCreateRequest;
import com.budgetmanagement.budgetmanagement.controller.expense.request.ExpenseUpdateRequest;
import com.budgetmanagement.budgetmanagement.controller.expense.response.ExpenseDetailResponse;
import com.budgetmanagement.budgetmanagement.controller.expense.ExpenseRecommendResponse;
import com.budgetmanagement.budgetmanagement.controller.expense.response.ExpensesResponse;
import com.budgetmanagement.budgetmanagement.expense.exception.ExpenseNotFoundException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
public class ExpenseServiceTest {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BudgetRepository budgetRepository;

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

    @Test
    @DisplayName("지출을 생성한다.")
    void createExpense() {
        //given
        String date = "2023-11-13 03:30:45";
        int amount = 10000;
        String category = "식비";
        String memo = "밥 먹음";
        ExpenseCreateRequest expenseCreateRequest = new ExpenseCreateRequest(date, amount, category, memo);

        //when
        expenseService.createExpense(user.getId(), expenseCreateRequest);

        //then
        List<Expense> expenses = expenseRepository.findAll();
        assertThat(expenses).isNotEmpty();
    }

    @Test
    @DisplayName("지출을 수정한다.")
    void updateExpense() {
        //given
        Expense expense = Expense.builder()
                .user(user)
                .date(LocalDateTime.now())
                .amount(1000)
                .category(BudgetCategoryType.FOOD)
                .memo("과자 사먹음")
                .build();
        expense = expenseRepository.save(expense);

        //when
        String date = "2023-11-13 03:30:45";
        int amount = 10000;
        String category = "식비";
        String memo = "과자 1000원치가 아니고 10000원치 사먹음";
        ExpenseUpdateRequest expenseUpdateRequest = new ExpenseUpdateRequest(date, amount, category, memo);
        expenseService.updateExpense(expense.getId(), expenseUpdateRequest);

        //then
        Expense foundExpense = expenseRepository.getById(expense.getId());
        assertThat(foundExpense.getAmount()).isEqualTo(10000);
    }

    @Nested
    @DisplayName("지출 목록을 가져온다.")
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
            String category = null;
            int minAmount = 0;
            int maxAmount = 10000;
            ExpensesResponse response = expenseService.getExpenses(user.getId(), category, minAmount, maxAmount);

            //then
            assertAll(() -> {
                assertThat(response.totalAmount()).isEqualTo(11000);
                assertThat(response.categoryTotalAmounts()).hasSize(2);
                assertThat(response.expenses()).hasSize(2);
            });
        }

        @Test
        @DisplayName("성공: 특정 카테고리만 가져온다.")
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
            expenseRepository.save(expense2);

            //when
            String category = "식비"; //식비 카테고리만 가져옴
            int minAmount = 0;
            int maxAmount = 10000;
            ExpensesResponse response = expenseService.getExpenses(user.getId(), category, minAmount, maxAmount);

            //then
            assertAll(() -> {
                assertThat(response.totalAmount()).isEqualTo(1000);
                assertThat(response.categoryTotalAmounts()).hasSize(1);
                assertThat(response.expenses()).hasSize(1);
                assertThat(response.expenses().get(0).category()).isEqualTo(category);
            });
        }

        @Test
        @DisplayName("성공: 최소금액과 최대금액 사이의 지출을 가져온다.")
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
                    .amount(10000)
                    .category(BudgetCategoryType.HEALTHCARE)
                    .memo("병원 갔다옴")
                    .build();
            expenseRepository.save(expense2);

            //when
            String category = null;
            int minAmount = 0;
            int maxAmount = 1000; //1000원 이하의 지출을 가져온다.
            ExpensesResponse response = expenseService.getExpenses(user.getId(), category, minAmount, maxAmount);

            //then
            assertAll(() -> {
                assertThat(response.totalAmount()).isEqualTo(1000);
                assertThat(response.categoryTotalAmounts()).hasSize(1);
                assertThat(response.expenses()).hasSize(1);
            });
        }
    }

    @Test
    @DisplayName("지출의 상세정보를 가져온다.")
    void getExpense() {
        //given
        Expense expense = Expense.builder()
                .user(user)
                .date(LocalDateTime.now())
                .amount(1000)
                .category(BudgetCategoryType.FOOD)
                .memo("과자 사먹음")
                .build();
        expense = expenseRepository.save(expense);

        //when
        ExpenseDetailResponse expenseDetailResponse = expenseService.getExpense(expense.getId());

        //then
        assertThat(expenseDetailResponse).isNotNull();
    }

    @Test
    @DisplayName("지출을 삭제한다.")
    void deleteExpense() {
        //given
        Expense expense = Expense.builder()
                .user(user)
                .date(LocalDateTime.now())
                .amount(1000)
                .category(BudgetCategoryType.FOOD)
                .memo("과자 사먹음")
                .build();
        expense = expenseRepository.save(expense);

        //when
        Long expenseId = expense.getId();
        expenseService.deleteExpense(expenseId);

        //then
        assertThatThrownBy(() -> expenseRepository.getById(expenseId))
                .isInstanceOf(ExpenseNotFoundException.class);
    }

    @Test
    @DisplayName("지출을 합계에서 제외시킨다.")
    void excludeExpense() {
        //given
        Expense expense = Expense.builder()
                .user(user)
                .date(LocalDateTime.now())
                .amount(1000)
                .category(BudgetCategoryType.FOOD)
                .memo("과자 사먹음")
                .build();
        expense = expenseRepository.save(expense);

        //when
        expenseService.excludeExpense(expense.getId());

        //then
        Expense foundExpense = expenseRepository.getById(expense.getId());
        assertThat(foundExpense.isExcluded()).isTrue();
    }

    @Test
    @DisplayName("사용가능한 지출을 추천한다.")
    void recommendExpense() {
        //given
        Budget shoppingCategory = Budget.builder()
                .user(user)
                .category(BudgetCategoryType.SHOPPING)
                .amount(10000)
                .ratio(50)
                .build();
        Budget financeCategory = Budget.builder()
                .user(user)
                .category(BudgetCategoryType.FINANCE)
                .amount(10000)
                .ratio(50)
                .build();
        List<Budget> categories = List.of(shoppingCategory, financeCategory);
        budgetRepository.saveAll(categories);

        Expense expense = Expense.builder()
                .user(user)
                .date(LocalDateTime.now())
                .amount(1000)
                .category(BudgetCategoryType.FOOD)
                .memo("과자 사먹음")
                .build();
        expenseRepository.save(expense);

        //when
        ExpenseRecommendResponse response = expenseService.recommendExpense(user.getId());

        //then
        int totalCategoryAmount = response.categoryAmounts().stream()
                .mapToInt(BudgetCategoryAmountResponse::amount)
                .sum();

        assertAll(() -> {
            assertThat(response).isNotNull();
            assertThat(response.categoryAmounts()).hasSize(2);
            assertThat(response.totalAmount()).isEqualTo(totalCategoryAmount);
        });
    }
}
