//package com.budgetmanagement.budgetmanagement.budget.application;
//
//import com.budgetmanagement.budgetmanagement.domain.budget.Budget;
//import com.budgetmanagement.budgetmanagement.controller.budget.request.BudgetConfigRequest;
//import com.budgetmanagement.budgetmanagement.controller.budget.request.BudgetAmountRequest;
//import com.budgetmanagement.budgetmanagement.controller.budget.BudgetUpdateRequest;
//import com.budgetmanagement.budgetmanagement.controller.budget.response.BudgetCategoryResponse;
//import com.budgetmanagement.budgetmanagement.controller.budget.response.BudgetRecommendResponse;
//import com.budgetmanagement.budgetmanagement.domain.budget.BudgetService;
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
//public class BudgetServiceTest {
//
//    @Autowired
//    private BudgetService budgetService;
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
//    @DisplayName("카테고리 목록을 반환한다.")
//    void getBudgetCategories() {
//        //given
//
//        //when
//        List<BudgetCategoryResponse> response = budgetService.getBudgetCategories();
//
//        //then
//        assertThat(response).isNotEmpty();
//    }
//
//    @Test
//    @DisplayName("예산을 등록한다.")
//    void createBudget() {
//        //given
//
//        //when
//        BudgetConfigRequest.BudgetByCategory shoppingCategory = new BudgetConfigRequest.BudgetByCategory("쇼핑", 101);
//        BudgetConfigRequest.BudgetByCategory financeCategory = new BudgetConfigRequest.BudgetByCategory("금융", 56);
//        BudgetConfigRequest.BudgetByCategory lifeCategory = new BudgetConfigRequest.BudgetByCategory("생활", 0);
//        List<BudgetConfigRequest.BudgetByCategory> categories = List.of(shoppingCategory, financeCategory, lifeCategory);
//        BudgetConfigRequest budgetConfigRequest = new BudgetConfigRequest(categories);
//
//        budgetService.createBudget(user.getId(), budgetConfigRequest);
//
//        //then
//        User foundUser = userRepository.getById(user.getId());
//        int ratioSum = foundUser.getBudgets().stream()
//                .mapToInt(Budget::getRatio)
//                .sum();
//
//        assertAll(() -> {
//            assertThat(budgetRepository.findAll()).isNotEmpty();
//            assertThat(foundUser.getBudgets()).isNotEmpty();
//            assertThat(ratioSum).isEqualTo(100); //카테고리 비율 체크
//        });
//    }
//
//    @Test
//    @DisplayName("예산을 수정하거나 추가한다.")
//    void updateBudget() {
//        //given
//        BudgetConfigRequest.BudgetByCategory shoppingCategory = new BudgetConfigRequest.BudgetByCategory("쇼핑", 101);
//        BudgetConfigRequest.BudgetByCategory financeCategory = new BudgetConfigRequest.BudgetByCategory("금융", 56);
//        BudgetConfigRequest.BudgetByCategory lifeCategory = new BudgetConfigRequest.BudgetByCategory("생활", 0);
//        List<BudgetConfigRequest.BudgetByCategory> categories = List.of(shoppingCategory, financeCategory, lifeCategory);
//        BudgetConfigRequest budgetConfigRequest = new BudgetConfigRequest(categories);
//
//        budgetService.createBudget(user.getId(), budgetConfigRequest);
//
//        //when
//        BudgetUpdateRequest.BudgetByCategory leisureCategory = new BudgetUpdateRequest.BudgetByCategory("여가", 10000);
//        BudgetUpdateRequest.BudgetByCategory healthcareCategory = new BudgetUpdateRequest.BudgetByCategory("의료/건강", 200000);
//        List<BudgetUpdateRequest.BudgetByCategory> updateCategory = List.of(leisureCategory, healthcareCategory);
//        BudgetUpdateRequest budgetUpdateRequest = new BudgetUpdateRequest(updateCategory);
//
//        budgetService.updateBudget(user.getId(), budgetUpdateRequest);
//
//        //then
//        User foundUser = userRepository.getById(user.getId());
//        int ratioSum = foundUser.getBudgets().stream()
//                .mapToInt(Budget::getRatio)
//                .sum();
//
//        assertAll(() -> {
//            assertThat(budgetRepository.findAll()).hasSize(2); //변경된 카테고리 개수 체크
//            assertThat(foundUser.getBudgets()).hasSize(2); //변경된 카테고리 개수 체크
//            assertThat(ratioSum).isEqualTo(100); //카테고리 비율 체크
//        });
//    }
//
//    @Test
//    @DisplayName("총액으로 카테고리 별 예산을 추천한다.")
//    void getRecommendationBudget() {
//        //given
//        BudgetConfigRequest.BudgetByCategory shoppingCategory = new BudgetConfigRequest.BudgetByCategory("쇼핑", 10000);
//        BudgetConfigRequest.BudgetByCategory financeCategory = new BudgetConfigRequest.BudgetByCategory("금융", 20000);
//        BudgetConfigRequest.BudgetByCategory lifeCategory = new BudgetConfigRequest.BudgetByCategory("생활", 0);
//        List<BudgetConfigRequest.BudgetByCategory> categories = List.of(shoppingCategory, financeCategory, lifeCategory);
//        BudgetConfigRequest budgetConfigRequest = new BudgetConfigRequest(categories);
//        budgetService.createBudget(user.getId(), budgetConfigRequest);
//
//        //다른 유저 생성 및 예산 설정
//        User anotherUser = User.builder()
//                .account("account2")
//                .password("password2")
//                .build();
//        userRepository.save(anotherUser);
//
//        BudgetConfigRequest.BudgetByCategory leisureCategory = new BudgetConfigRequest.BudgetByCategory("여가", 50000);
//        BudgetConfigRequest.BudgetByCategory healthcareCategory = new BudgetConfigRequest.BudgetByCategory("의료/건강", 10000);
//        List<BudgetConfigRequest.BudgetByCategory> updateCategory = List.of(leisureCategory, healthcareCategory);
//        BudgetConfigRequest budgetConfigRequest2 = new BudgetConfigRequest(updateCategory);
//
//        budgetService.createBudget(anotherUser.getId(), budgetConfigRequest2);
//
//        //when
//        BudgetAmountRequest budgetAmountRequest = new BudgetAmountRequest(100000);
//        List<BudgetRecommendResponse> response = budgetService.getRecommendationBudget(budgetAmountRequest);
//
//        //then
//        int totalAmount = response.stream()
//                .mapToInt(BudgetRecommendResponse::amount)
//                .sum();
//
//        assertAll(() -> {
//            assertThat(response).hasSize(5); //총 카테고리 개수 체크
//            assertThat(totalAmount).isEqualTo(100000); //요청한 총액과 같은지 체크
//        });
//    }
//}
