//package com.budgetmanagement.budgetmanagement.budget.repository;
//
//import com.budgetmanagement.budgetmanagement.domain.budget.Budget;
//import com.budgetmanagement.budgetmanagement.domain.budget.category.BudgetCategoryType;
//import com.budgetmanagement.budgetmanagement.controller.budget.BudgetCategoryAmountResponse;
//import com.budgetmanagement.budgetmanagement.controller.budget.response.BudgetRecommendResponse;
//import com.budgetmanagement.budgetmanagement.domain.budget.BudgetQueryRepository;
//import com.budgetmanagement.budgetmanagement.domain.budget.BudgetRepository;
//import com.budgetmanagement.budgetmanagement.domain.user.User;
//import com.budgetmanagement.budgetmanagement.domain.user.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertAll;
//
//@SpringBootTest
//@Transactional
//public class BudgetQueryRepositoryTest {
//
//    @Autowired
//    private BudgetQueryRepository budgetQueryRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BudgetRepository budgetRepository;
//
//    private User user;
//
//    @BeforeEach
//    void saveUser() {
//        User newUser = User.builder()
//                .account("account")
//                .password("password")
//                .build();
//        userRepository.save(newUser);
//        user = newUser;
//    }
//
//    @Test
//    @DisplayName("총액으로 기존 유저들이 설정한 카테고리 별 예산을 통계하여 금액으로 치환 후 반환한다.")
//    void test() {
//        //given
//        Budget shoppingCategory = Budget.builder()
//                .user(user)
//                .category(BudgetCategoryType.SHOPPING)
//                .amount(10000)
//                .ratio(50)
//                .build();
//        Budget financeCategory = Budget.builder()
//                .user(user)
//                .category(BudgetCategoryType.FINANCE)
//                .amount(10000)
//                .ratio(50)
//                .build();
//        List<Budget> categories = List.of(shoppingCategory, financeCategory);
//        budgetRepository.saveAll(categories);
//
//        //when
//        int totalAmount = 500000;
//        int userCount = userRepository.findAll().size();
//        List<BudgetRecommendResponse> response = budgetQueryRepository.getRecommendationBudget(totalAmount, userCount);
//
//        //then
//        int actualTotalAmount = response.stream()
//                .mapToInt(BudgetRecommendResponse::amount)
//                .sum();
//        assertAll(() -> {
//            assertThat(response).hasSize(2);
//            assertThat(actualTotalAmount).isEqualTo(totalAmount);
//        });
//    }
//
//    @Test
//    @DisplayName("총액으로 카테고리 별 금액을 계산하여 반환한다.")
//    void getCategoryAmounts() {
//        //given
//        Budget shoppingCategory = Budget.builder()
//                .user(user)
//                .category(BudgetCategoryType.SHOPPING)
//                .amount(10000)
//                .ratio(50)
//                .build();
//        Budget financeCategory = Budget.builder()
//                .user(user)
//                .category(BudgetCategoryType.FINANCE)
//                .amount(10000)
//                .ratio(50)
//                .build();
//        List<Budget> categories = List.of(shoppingCategory, financeCategory);
//        budgetRepository.saveAll(categories);
//
//        //when
//        int totalAmount = 100000;
//        List<BudgetCategoryAmountResponse> response = budgetQueryRepository.getCategoryAmounts(totalAmount, user);
//
//        //then
//        int totalCategoryAmount = response.stream()
//                .mapToInt(BudgetCategoryAmountResponse::amount)
//                .sum();
//
//        assertAll(() -> {
//            assertThat(response).hasSize(2);
//            assertThat(totalCategoryAmount).isEqualTo(totalAmount);
//        });
//    }
//}
