package com.budgetmanagement.budgetmanagement.user.controller;

import com.budgetmanagement.budgetmanagement.common.ControllerTest;
import com.budgetmanagement.budgetmanagement.controller.user.request.UserJoinRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends ControllerTest {

    @Nested
    @DisplayName("회원가입을 한다.")
    class join {

        @Test
        @DisplayName("성공")
        void success() throws Exception {
            willDoNothing()
                    .given(userService)
                    .join(any());

            UserJoinRequest userJoinRequest = new UserJoinRequest("account", "test@2123#@");

            mockMvc.perform(post("/users/join")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userJoinRequest)))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        }

        @ParameterizedTest
        //1: 빈값, 2: 빈값2, 3: 10자리 이하, 4: 숫자, 문자, 특수문자 중 2가지 이상 포함안됨
        @ValueSource(strings = {"", " ", "test@2123", "skatpdnjsdlqslek"})
        @DisplayName("실패: 잘못된 비밀번호 양식")
        void fail(String invalidPassword) throws Exception {
            willDoNothing()
                    .given(userService)
                    .join(any());

            UserJoinRequest userJoinRequest = new UserJoinRequest("account", invalidPassword);

            mockMvc.perform(post("/users/join")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userJoinRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }
}
