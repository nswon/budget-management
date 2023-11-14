package com.budgetmanagement.budgetmanagement.expense.controller;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategory;
import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetCategoryAmountResponse;
import com.budgetmanagement.budgetmanagement.common.ControllerTest;
import com.budgetmanagement.budgetmanagement.expense.domain.ExpenseMessage;
import com.budgetmanagement.budgetmanagement.expense.dto.request.ExpenseCreateRequest;
import com.budgetmanagement.budgetmanagement.expense.dto.request.ExpenseUpdateRequest;
import com.budgetmanagement.budgetmanagement.expense.dto.response.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExpenseControllerTest extends ControllerTest {

    @Test
    @DisplayName("지출을 생성한다.")
    void createExpense() throws Exception {
        willDoNothing()
                .given(expenseService)
                .createExpense(any(), any());

        ExpenseCreateRequest request = new ExpenseCreateRequest("2023-11-13 11:38:40", 1000, "식비", "간식");
        mockMvc.perform(post("/expenses")
                .header("Authorization", "Bearer aaaaaaaa.bbbbbbbb.cccccccc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("지출을 수정한다.")
    void updateExpense() throws Exception {
        willDoNothing()
                .given(expenseService)
                .updateExpense(any(), any());

        ExpenseUpdateRequest request = new ExpenseUpdateRequest("2023-11-13 11:38:40", 2000, "식비", "간식");
        mockMvc.perform(put("/expenses" + "/{id}", 1)
                        .header("Authorization", "Bearer aaaaaaaa.bbbbbbbb.cccccccc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("지출 목록을 조회한다.")
    void getExpenses() throws Exception {
        List<ExpenseCategoryTotalAmountResponse> categoryTotalAmounts = List.of(new ExpenseCategoryTotalAmountResponse(100));
        List<ExpenseResponse> expenses = List.of(new ExpenseResponse(LocalDateTime.now(), 100, BudgetCategory.FOOD));
        ExpensesResponse response = new ExpensesResponse(100, categoryTotalAmounts, expenses);

        given(expenseService.getExpenses(any(), any(), anyInt(), anyInt()))
                .willReturn(response);

        mockMvc.perform(get("/expenses")
                .header("Authorization", "Bearer aaaaaaaa.bbbbbbbb.cccccccc"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("지출을 상세조회한다.")
    void getExpense() throws Exception {
        ExpenseDetailResponse response = new ExpenseDetailResponse(LocalDateTime.now(), 100, "식비", "메모", false);

        given(expenseService.getExpense(any()))
                .willReturn(response);

        mockMvc.perform(get("/expenses" + "/{id}", 1)
                        .header("Authorization", "Bearer aaaaaaaa.bbbbbbbb.cccccccc"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("지출을 삭제한다.")
    void deleteExpense() throws Exception {
        willDoNothing()
                .given(expenseService)
                .deleteExpense(any());

        mockMvc.perform(delete("/expenses" + "/{id}", 1)
                        .header("Authorization", "Bearer aaaaaaaa.bbbbbbbb.cccccccc"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("지출을 합계에서 제외시킨다.")
    void excludeExpense() throws Exception {
        willDoNothing()
                .given(expenseService)
                .excludeExpense(any());

        mockMvc.perform(patch("/expenses" + "/{id}", 1)
                        .header("Authorization", "Bearer aaaaaaaa.bbbbbbbb.cccccccc"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("오늘 사용 가능한 지출을 추천한다.")
    void recommendExpense() throws Exception {
        List<BudgetCategoryAmountResponse> categoryAmounts = List.of(new BudgetCategoryAmountResponse("식비", 5000));
        ExpenseRecommendResponse response = new ExpenseRecommendResponse(1000, categoryAmounts, ExpenseMessage.GOOD.getMessage());

        given(expenseService.recommendExpense(any()))
                .willReturn(response);

        mockMvc.perform(get("/expenses/today/recommendation")
                        .header("Authorization", "Bearer aaaaaaaa.bbbbbbbb.cccccccc"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
