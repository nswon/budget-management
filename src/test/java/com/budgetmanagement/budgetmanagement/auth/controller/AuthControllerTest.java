package com.budgetmanagement.budgetmanagement.auth.controller;

import com.budgetmanagement.budgetmanagement.controller.auth.request.AuthLoginRequest;
import com.budgetmanagement.budgetmanagement.controller.auth.request.AuthRenewalTokenRequest;
import com.budgetmanagement.budgetmanagement.controller.auth.response.AuthRenewalTokenResponse;
import com.budgetmanagement.budgetmanagement.controller.auth.response.AuthTokenResponse;
import com.budgetmanagement.budgetmanagement.auth.exception.InvalidPasswordException;
import com.budgetmanagement.budgetmanagement.common.ControllerTest;
import com.budgetmanagement.budgetmanagement.user.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends ControllerTest {

    @Nested
    @DisplayName("로그인을 한다.")
    class login {

        @Test
        @DisplayName("성공")
        void success() throws Exception {
            AuthTokenResponse response = new AuthTokenResponse("accessToken", "refreshToken");
            given(authService.login(any()))
                    .willReturn(response);

            AuthLoginRequest authLoginRequest = new AuthLoginRequest("account", "password");
            mockMvc.perform(post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(authLoginRequest)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("실패: 존재하지 않는 계정")
        void fail() throws Exception {
            when(authService.login(any()))
                    .thenThrow(new UserNotFoundException());

            AuthLoginRequest authLoginRequest = new AuthLoginRequest("invalid", "password");
            mockMvc.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(authLoginRequest)))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("실패: 잘못된 비밀번호")
        void fail2() throws Exception {
            when(authService.login(any()))
                    .thenThrow(new InvalidPasswordException());

            AuthLoginRequest authLoginRequest = new AuthLoginRequest("account", "invalid");
            mockMvc.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(authLoginRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("새로운 엑세스 토큰을 발급한다.")
    class generateRenewalAccessToken {

        @Test
        @DisplayName("성공")
        void success() throws Exception {
            AuthRenewalTokenResponse response = new AuthRenewalTokenResponse("newAccessToken");
            given(authService.generateRenewalAccessToken(any()))
                    .willReturn(response);

            AuthRenewalTokenRequest request = new AuthRenewalTokenRequest("refresh");
            mockMvc.perform(post("/auth/renewal/access")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }
}
