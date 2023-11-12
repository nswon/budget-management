package com.budgetmanagement.budgetmanagement.budget.repository;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategory;
import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategoryType;
import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetRecommendationResponse;
import com.budgetmanagement.budgetmanagement.user.domain.User;
import com.budgetmanagement.budgetmanagement.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
public class BudgetCategoryQRepositoryTest {

    @Autowired
    private BudgetCategoryQRepository budgetCategoryQRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BudgetCategoryRepository budgetCategoryRepository;

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
    @DisplayName("총액으로 기존 유저들이 설정한 카테고리 별 예산을 통계하여 금액으로 치환 후 반환")
    void test() {
        //given
        BudgetCategory shoppingCategory = BudgetCategory.builder()
                .user(user)
                .categoryType(BudgetCategoryType.SHOPPING)
                .amount(10000)
                .ratio(50)
                .build();
        BudgetCategory financeCategory = BudgetCategory.builder()
                .user(user)
                .categoryType(BudgetCategoryType.FINANCE)
                .amount(10000)
                .ratio(50)
                .build();
        List<BudgetCategory> categories = List.of(shoppingCategory, financeCategory);
        budgetCategoryRepository.saveAll(categories);

        //when
        int totalAmount = 500000;
        int userCount = userRepository.findAll().size();
        List<BudgetRecommendationResponse> response = budgetCategoryQRepository.getRecommendationBudget(totalAmount, userCount);

        //then
        int actualTotalAmount = response.stream()
                .mapToInt(BudgetRecommendationResponse::amount)
                .sum();
        assertAll(() -> {
            assertThat(response).hasSize(2);
            assertThat(actualTotalAmount).isEqualTo(totalAmount);
        });
    }
}
