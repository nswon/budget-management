package com.budgetmanagement.budgetmanagement.budget.application;

import com.budgetmanagement.budgetmanagement.budget.domain.Budget;
import com.budgetmanagement.budgetmanagement.budget.dto.request.BudgetCreateRequest;
import com.budgetmanagement.budgetmanagement.budget.dto.request.BudgetTotalAmountRequest;
import com.budgetmanagement.budgetmanagement.budget.dto.request.BudgetUpdateRequest;
import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetCategoryResponse;
import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetRecommendResponse;
import com.budgetmanagement.budgetmanagement.budget.repository.BudgetRepository;
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
public class BudgetServiceTest {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BudgetRepository budgetRepository;

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
    @DisplayName("카테고리 목록을 반환한다.")
    void getBudgetCategories() {
        //given

        //when
        List<BudgetCategoryResponse> response = budgetService.getBudgetCategories();

        //then
        assertThat(response).isNotEmpty();
    }

    @Test
    @DisplayName("예산을 등록한다.")
    void createBudget() {
        //given

        //when
        BudgetCreateRequest.BudgetByCategory shoppingCategory = new BudgetCreateRequest.BudgetByCategory("쇼핑", 101);
        BudgetCreateRequest.BudgetByCategory financeCategory = new BudgetCreateRequest.BudgetByCategory("금융", 56);
        BudgetCreateRequest.BudgetByCategory lifeCategory = new BudgetCreateRequest.BudgetByCategory("생활", 0);
        List<BudgetCreateRequest.BudgetByCategory> categories = List.of(shoppingCategory, financeCategory, lifeCategory);
        BudgetCreateRequest budgetCreateRequest = new BudgetCreateRequest(categories);

        budgetService.createBudget(user.getId(), budgetCreateRequest);

        //then
        User foundUser = userRepository.getById(user.getId());
        int ratioSum = foundUser.getBudgets().stream()
                .mapToInt(Budget::getRatio)
                .sum();

        assertAll(() -> {
            assertThat(budgetRepository.findAll()).isNotEmpty();
            assertThat(foundUser.getBudgets()).isNotEmpty();
            assertThat(ratioSum).isEqualTo(100); //카테고리 비율 체크
        });
    }

    @Test
    @DisplayName("예산을 수정하거나 추가한다.")
    void updateBudget() {
        //given
        BudgetCreateRequest.BudgetByCategory shoppingCategory = new BudgetCreateRequest.BudgetByCategory("쇼핑", 101);
        BudgetCreateRequest.BudgetByCategory financeCategory = new BudgetCreateRequest.BudgetByCategory("금융", 56);
        BudgetCreateRequest.BudgetByCategory lifeCategory = new BudgetCreateRequest.BudgetByCategory("생활", 0);
        List<BudgetCreateRequest.BudgetByCategory> categories = List.of(shoppingCategory, financeCategory, lifeCategory);
        BudgetCreateRequest budgetCreateRequest = new BudgetCreateRequest(categories);

        budgetService.createBudget(user.getId(), budgetCreateRequest);

        //when
        BudgetUpdateRequest.BudgetByCategory leisureCategory = new BudgetUpdateRequest.BudgetByCategory("여가", 10000);
        BudgetUpdateRequest.BudgetByCategory healthcareCategory = new BudgetUpdateRequest.BudgetByCategory("의료/건강", 200000);
        List<BudgetUpdateRequest.BudgetByCategory> updateCategory = List.of(leisureCategory, healthcareCategory);
        BudgetUpdateRequest budgetUpdateRequest = new BudgetUpdateRequest(updateCategory);

        budgetService.updateBudget(user.getId(), budgetUpdateRequest);

        //then
        User foundUser = userRepository.getById(user.getId());
        int ratioSum = foundUser.getBudgets().stream()
                .mapToInt(Budget::getRatio)
                .sum();

        assertAll(() -> {
            assertThat(budgetRepository.findAll()).hasSize(2); //변경된 카테고리 개수 체크
            assertThat(foundUser.getBudgets()).hasSize(2); //변경된 카테고리 개수 체크
            assertThat(ratioSum).isEqualTo(100); //카테고리 비율 체크
        });
    }

    @Test
    @DisplayName("총액으로 카테고리 별 예산을 추천한다.")
    void getRecommendationBudget() {
        //given
        BudgetCreateRequest.BudgetByCategory shoppingCategory = new BudgetCreateRequest.BudgetByCategory("쇼핑", 10000);
        BudgetCreateRequest.BudgetByCategory financeCategory = new BudgetCreateRequest.BudgetByCategory("금융", 20000);
        BudgetCreateRequest.BudgetByCategory lifeCategory = new BudgetCreateRequest.BudgetByCategory("생활", 0);
        List<BudgetCreateRequest.BudgetByCategory> categories = List.of(shoppingCategory, financeCategory, lifeCategory);
        BudgetCreateRequest budgetCreateRequest = new BudgetCreateRequest(categories);
        budgetService.createBudget(user.getId(), budgetCreateRequest);

        //다른 유저 생성 및 예산 설정
        User anotherUser = User.builder()
                .account("account2")
                .password("password2")
                .build();
        userRepository.save(anotherUser);

        BudgetCreateRequest.BudgetByCategory leisureCategory = new BudgetCreateRequest.BudgetByCategory("여가", 50000);
        BudgetCreateRequest.BudgetByCategory healthcareCategory = new BudgetCreateRequest.BudgetByCategory("의료/건강", 10000);
        List<BudgetCreateRequest.BudgetByCategory> updateCategory = List.of(leisureCategory, healthcareCategory);
        BudgetCreateRequest budgetCreateRequest2 = new BudgetCreateRequest(updateCategory);

        budgetService.createBudget(anotherUser.getId(), budgetCreateRequest2);

        //when
        BudgetTotalAmountRequest budgetTotalAmountRequest = new BudgetTotalAmountRequest(100000);
        List<BudgetRecommendResponse> response = budgetService.getRecommendationBudget(budgetTotalAmountRequest);

        //then
        int totalAmount = response.stream()
                .mapToInt(BudgetRecommendResponse::amount)
                .sum();

        assertAll(() -> {
            assertThat(response).hasSize(5); //총 카테고리 개수 체크
            assertThat(totalAmount).isEqualTo(100000); //요청한 총액과 같은지 체크
        });
    }
}
