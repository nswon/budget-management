package com.budgetmanagement.budgetmanagement.budget.controller;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategory;
import com.budgetmanagement.budgetmanagement.budget.dto.request.BudgetCreateRequest;
import com.budgetmanagement.budgetmanagement.budget.dto.request.BudgetTotalAmountRequest;
import com.budgetmanagement.budgetmanagement.budget.dto.request.BudgetUpdateRequest;
import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetCategoryResponse;
import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetRecommendResponse;
import com.budgetmanagement.budgetmanagement.common.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BudgetControllerTest extends ControllerTest {

    @Test
    @DisplayName("예산 카테고리 목록을 조회한다.")
    void getBudgetCategories() throws Exception {
        List<BudgetCategoryResponse> response = List.of(new BudgetCategoryResponse(BudgetCategory.FOOD));
        given(budgetService.getBudgetCategories())
                .willReturn(response);

        mockMvc.perform(get("/budget")
                        .header("Authorization", "Bearer aaaaaaaa.bbbbbbbb.cccccccc"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Nested
    @DisplayName("예산을 등록한다.")
    class createBudget {

        @Test
        @DisplayName("성공")
        void success() throws Exception {
            willDoNothing()
                    .given(budgetService)
                    .createBudget(any(), any());

            BudgetCreateRequest.BudgetByCategory shoppingCategory = new BudgetCreateRequest.BudgetByCategory("쇼핑", 101);
            BudgetCreateRequest.BudgetByCategory financeCategory = new BudgetCreateRequest.BudgetByCategory("금융", 56);
            BudgetCreateRequest.BudgetByCategory lifeCategory = new BudgetCreateRequest.BudgetByCategory("생활", 0);
            List<BudgetCreateRequest.BudgetByCategory> categories = List.of(shoppingCategory, financeCategory, lifeCategory);
            BudgetCreateRequest budgetCreateRequest = new BudgetCreateRequest(categories);

            mockMvc.perform(post("/budget")
                            .header("Authorization", "Bearer aaaaaaaa.bbbbbbbb.cccccccc")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(budgetCreateRequest)))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("실패: 0원 미만인 금액")
        void fail() throws Exception {
            willDoNothing()
                    .given(budgetService)
                    .createBudget(any(), any());

            int invalidAmount = -1;
            BudgetCreateRequest.BudgetByCategory shoppingCategory = new BudgetCreateRequest.BudgetByCategory("쇼핑", invalidAmount);
            BudgetCreateRequest.BudgetByCategory financeCategory = new BudgetCreateRequest.BudgetByCategory("금융", invalidAmount);
            BudgetCreateRequest.BudgetByCategory lifeCategory = new BudgetCreateRequest.BudgetByCategory("생활", invalidAmount);
            List<BudgetCreateRequest.BudgetByCategory> categories = List.of(shoppingCategory, financeCategory, lifeCategory);
            BudgetCreateRequest budgetCreateRequest = new BudgetCreateRequest(categories);

            mockMvc.perform(post("/budget")
                            .header("Authorization", "Bearer aaaaaaaa.bbbbbbbb.cccccccc")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(budgetCreateRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("예산을 수정한다.")
    class updateBudget {

        @Test
        @DisplayName("성공")
        void success() throws Exception {
            willDoNothing()
                    .given(budgetService)
                    .updateBudget(any(), any());

            BudgetUpdateRequest.BudgetByCategory shoppingCategory = new BudgetUpdateRequest.BudgetByCategory("쇼핑", 101);
            BudgetUpdateRequest.BudgetByCategory financeCategory = new BudgetUpdateRequest.BudgetByCategory("금융", 56);
            BudgetUpdateRequest.BudgetByCategory lifeCategory = new BudgetUpdateRequest.BudgetByCategory("생활", 0);
            List<BudgetUpdateRequest.BudgetByCategory> categories = List.of(shoppingCategory, financeCategory, lifeCategory);
            BudgetUpdateRequest budgetCreateRequest = new BudgetUpdateRequest(categories);

            mockMvc.perform(put("/budget")
                            .header("Authorization", "Bearer aaaaaaaa.bbbbbbbb.cccccccc")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(budgetCreateRequest)))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("실패: 0원 미만인 금액")
        void fail() throws Exception {
            willDoNothing()
                    .given(budgetService)
                    .updateBudget(any(), any());

            BudgetUpdateRequest.BudgetByCategory shoppingCategory = new BudgetUpdateRequest.BudgetByCategory("쇼핑", -1);
            BudgetUpdateRequest.BudgetByCategory financeCategory = new BudgetUpdateRequest.BudgetByCategory("금융", -1);
            BudgetUpdateRequest.BudgetByCategory lifeCategory = new BudgetUpdateRequest.BudgetByCategory("생활", -1);
            List<BudgetUpdateRequest.BudgetByCategory> categories = List.of(shoppingCategory, financeCategory, lifeCategory);
            BudgetUpdateRequest budgetCreateRequest = new BudgetUpdateRequest(categories);

            mockMvc.perform(put("/budget")
                            .header("Authorization", "Bearer aaaaaaaa.bbbbbbbb.cccccccc")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(budgetCreateRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("총액으로 카테고리 별 예산을 추천한다.")
    class getRecommendationBudget {

        @Test
        @DisplayName("성공")
        void success() throws Exception {
            List<BudgetRecommendResponse> response = List.of(new BudgetRecommendResponse("금융", 1000));

            given(budgetService.getRecommendationBudget(any()))
                    .willReturn(response);

            BudgetTotalAmountRequest request = new BudgetTotalAmountRequest(1000);
            mockMvc.perform(get("/budget/recommendation")
                            .header("Authorization", "Bearer aaaaaaaa.bbbbbbbb.cccccccc")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("실패: 0원 미만인 총액")
        void fail() throws Exception {
            List<BudgetRecommendResponse> response = List.of(new BudgetRecommendResponse("금융", 1000));

            given(budgetService.getRecommendationBudget(any()))
                    .willReturn(response);

            BudgetTotalAmountRequest request = new BudgetTotalAmountRequest(-1);
            mockMvc.perform(get("/budget/recommendation")
                            .header("Authorization", "Bearer aaaaaaaa.bbbbbbbb.cccccccc")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }
}
