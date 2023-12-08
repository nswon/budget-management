package com.budgetmanagement.budgetmanagement.auth.application;

import com.budgetmanagement.budgetmanagement.domain.auth.AuthService;
import com.budgetmanagement.budgetmanagement.controller.auth.request.AuthLoginRequest;
import com.budgetmanagement.budgetmanagement.controller.auth.request.AuthRenewalTokenRequest;
import com.budgetmanagement.budgetmanagement.controller.auth.response.AuthRenewalTokenResponse;
import com.budgetmanagement.budgetmanagement.controller.auth.response.AuthTokenResponse;
import com.budgetmanagement.budgetmanagement.auth.exception.InvalidPasswordException;
import com.budgetmanagement.budgetmanagement.auth.exception.InvalidTokenException;
import com.budgetmanagement.budgetmanagement.domain.user.User;
import com.budgetmanagement.budgetmanagement.user.exception.UserNotFoundException;
import com.budgetmanagement.budgetmanagement.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void saveUser() {
        User user = User.builder()
                .account("account")
                .password(passwordEncoder.encode("password"))
                .build();
        userRepository.save(user);
    }

    @Nested
    @DisplayName("로그인을 하면 토큰을 발급하고 반환한다.")
    class login {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            AuthLoginRequest authLoginRequest = new AuthLoginRequest("account", "password");

            //when
            AuthTokenResponse authTokenResponse = authService.login(authLoginRequest);

            //then
            assertAll(() -> {
                assertThat(authTokenResponse.accessToken()).isNotEmpty();
                assertThat(authTokenResponse.refreshToken()).isNotEmpty();
            });
        }

        @Test
        @DisplayName("성공: 이미 가입된 회원이 한번 더 로그인을 하면 저장된 리프래쉬 토큰을 가져온다.")
        void success2() {
            //given
            AuthLoginRequest authLoginRequest = new AuthLoginRequest("account", "password");
            AuthTokenResponse authTokenResponse = authService.login(authLoginRequest);

            //when
            AuthTokenResponse authTokenResponse2 = authService.login(authLoginRequest);

            //then
            assertThat(authTokenResponse.refreshToken()).isEqualTo(authTokenResponse2.refreshToken());
        }

        @Test
        @DisplayName("실패: 존재하지 않는 계정")
        void fail() {
            //given
            String invalidAccount = "invalid";

            //when, then
            AuthLoginRequest authLoginRequest = new AuthLoginRequest(invalidAccount, "password");
            assertThatThrownBy(() -> authService.login(authLoginRequest))
                    .isInstanceOf(UserNotFoundException.class);
        }

        @Test
        @DisplayName("실패: 잘못된 비밀번호")
        void fail2() {
            //given
            String invalidPassword = "invalid";

            //when, then
            AuthLoginRequest authLoginRequest = new AuthLoginRequest("account", invalidPassword);
            assertThatThrownBy(() -> authService.login(authLoginRequest))
                    .isInstanceOf(InvalidPasswordException.class);
        }
    }

    @Nested
    @DisplayName("리프래쉬 토큰으로 새로운 엑세스 토큰을 발급한다.")
    class generateRenewalAccessToken {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            AuthLoginRequest authLoginRequest = new AuthLoginRequest("account", "password");
            AuthTokenResponse authTokenResponse = authService.login(authLoginRequest);

            //when
            String refreshToken = authTokenResponse.refreshToken();
            AuthRenewalTokenRequest authRenewalTokenRequest = new AuthRenewalTokenRequest(refreshToken);
            AuthRenewalTokenResponse response = authService.generateRenewalAccessToken(authRenewalTokenRequest);

            //then
            assertThat(response.accessToken()).isNotEmpty();
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "invalid"})
        @DisplayName("실패:  비어있거나 잘못된 리프래쉬 토큰")
        void fail(String invalidRefreshToken) {
            //given

            //when, then
            AuthRenewalTokenRequest authRenewalTokenRequest = new AuthRenewalTokenRequest(invalidRefreshToken);
            assertThatThrownBy(() -> authService.generateRenewalAccessToken(authRenewalTokenRequest))
                    .isInstanceOf(InvalidTokenException.class);
        }
    }

    @Nested
    @DisplayName("엑세스 토큰에서 유저의 정보를 가져온다.")
    class extractUserId {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            AuthLoginRequest authLoginRequest = new AuthLoginRequest("account", "password");
            AuthTokenResponse authTokenResponse = authService.login(authLoginRequest);

            //when
            Long userId = authService.extractUserId(authTokenResponse.accessToken());

            //then
            User user = userRepository.getByAccount("account");
            assertThat(userId).isEqualTo(user.getId());
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "invalid"})
        @DisplayName("실패: 비어있거나 잘못된 엑세스 토큰")
        void fail(String invalidAccessToken) {
            //given

            //when, then
            assertThatThrownBy(() -> authService.extractUserId(invalidAccessToken))
                    .isInstanceOf(InvalidTokenException.class);
        }
    }
}
