package com.budgetmanagement.budgetmanagement.common;

import com.budgetmanagement.budgetmanagement.auth.domain.AuthService;
import com.budgetmanagement.budgetmanagement.auth.api.AuthController;
import com.budgetmanagement.budgetmanagement.budget.application.BudgetService;
import com.budgetmanagement.budgetmanagement.budget.controller.BudgetController;
import com.budgetmanagement.budgetmanagement.expense.application.ExpenseService;
import com.budgetmanagement.budgetmanagement.expense.controller.ExpenseController;
import com.budgetmanagement.budgetmanagement.user.domain.UserService;
import com.budgetmanagement.budgetmanagement.user.api.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest({
        UserController.class,
        AuthController.class,
        BudgetController.class,
        ExpenseController.class
})
public class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected BudgetService budgetService;

    @MockBean
    protected ExpenseService expenseService;
}
