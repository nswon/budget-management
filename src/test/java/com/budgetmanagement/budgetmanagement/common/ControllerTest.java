package com.budgetmanagement.budgetmanagement.common;

import com.budgetmanagement.budgetmanagement.domain.auth.AuthService;
import com.budgetmanagement.budgetmanagement.controller.auth.AuthController;
import com.budgetmanagement.budgetmanagement.domain.budget.BudgetService;
import com.budgetmanagement.budgetmanagement.controller.budget.BudgetController;
import com.budgetmanagement.budgetmanagement.domain.expense.ExpenseService;
import com.budgetmanagement.budgetmanagement.controller.expense.ExpenseController;
import com.budgetmanagement.budgetmanagement.domain.user.UserService;
import com.budgetmanagement.budgetmanagement.controller.user.UserController;
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
