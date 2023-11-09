package com.budgetmanagement.budgetmanagement.common;

import com.budgetmanagement.budgetmanagement.auth.application.AuthService;
import com.budgetmanagement.budgetmanagement.auth.controller.AuthController;
import com.budgetmanagement.budgetmanagement.user.application.UserService;
import com.budgetmanagement.budgetmanagement.user.controller.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest({
        UserController.class,
        AuthController.class
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
}
