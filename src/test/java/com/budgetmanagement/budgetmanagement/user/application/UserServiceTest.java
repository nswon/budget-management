package com.budgetmanagement.budgetmanagement.user.application;

import com.budgetmanagement.budgetmanagement.domain.user.User;
import com.budgetmanagement.budgetmanagement.controller.user.request.UserJoinRequest;
import com.budgetmanagement.budgetmanagement.domain.user.UserService;
import com.budgetmanagement.budgetmanagement.user.exception.DuplicateAccountException;
import com.budgetmanagement.budgetmanagement.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Nested
    @DisplayName("회원가입을 한다.")
    class join {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            UserJoinRequest request = new UserJoinRequest("account", "password123@");

            //when
            userService.join(request.toUserRequest());

            //then
            assertThat(userRepository.existsByAccount("account")).isTrue();
        }

        @Test
        @DisplayName("실패: 이미 존재하는 계정으로 회원가입")
        void fail() {
            //given
            String account = "account";
            User user = User.builder()
                    .account(account)
                    .password("password")
                    .build();
            userRepository.save(user);

            //when, then
            UserJoinRequest request = new UserJoinRequest(account, "password123@");
            assertThatThrownBy(() -> userService.join(request.toUserRequest()))
                    .isInstanceOf(DuplicateAccountException.class);
        }
    }
}
