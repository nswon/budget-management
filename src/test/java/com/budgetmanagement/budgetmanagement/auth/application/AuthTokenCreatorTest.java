package com.budgetmanagement.budgetmanagement.auth.application;

import com.budgetmanagement.budgetmanagement.auth.dto.AuthToken;
import com.budgetmanagement.budgetmanagement.auth.exception.InvalidTokenException;
import com.budgetmanagement.budgetmanagement.auth.repository.AuthTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class AuthTokenCreatorTest {

    @Autowired
    AuthTokenCreator authTokenCreator;

    @Autowired
    AuthTokenRepository authTokenRepository;

    @Nested
    @DisplayName("유저 id로 엑세스 토큰과 리프래쉬 토큰을 생성한다.")
    class createAuthToken {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            Long userId = 1L;

            //when
            AuthToken authToken = authTokenCreator.createAuthToken(userId);

            //then
            assertAll(() -> {
                assertThat(authToken.accessToken()).isNotEmpty();
                assertThat(authToken.refreshToken()).isNotEmpty();
            });
        }

        @Test
        @DisplayName("성공: 이미 가입한 유저라면 같은 리프래쉬 토큰을 발급한다.")
        void success2() {
            //given
            Long userId = 1L;
            String refreshToken = "refreshToken";
            authTokenRepository.save(userId, refreshToken);

            //when
            AuthToken authToken = authTokenCreator.createAuthToken(userId);

            //then
            assertThat(authToken.refreshToken()).isEqualTo(refreshToken);
        }
    }

    @Nested
    @DisplayName("리프래쉬 토큰으로 새로운 엑세스 토큰을 발급한다.")
    class renewAuthToken {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            Long userId = 1L;
            AuthToken authToken = authTokenCreator.createAuthToken(userId);

            //when
            String refreshToken = authToken.refreshToken();
            AuthToken renewAuthToken = authTokenCreator.renewAuthToken(refreshToken);

            //then
            assertAll(() -> {
                assertThat(renewAuthToken.accessToken()).isNotEmpty();
                assertThat(renewAuthToken.refreshToken()).isNotEmpty();
                assertThat(renewAuthToken.refreshToken()).isEqualTo(refreshToken);
            });
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "invalid"})
        @DisplayName("실패: 비어있거나 잘못된 리프래쉬 토큰")
        void fail(String invalidRefreshToken) {
            //given

            //when, then
            assertThatThrownBy(() -> authTokenCreator.renewAuthToken(invalidRefreshToken))
                    .isInstanceOf(InvalidTokenException.class);
        }
    }

    @Nested
    @DisplayName("토큰에서 페이로드를 추출한다.")
    class extractPayload {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            Long userId = 1L;
            AuthToken authToken = authTokenCreator.createAuthToken(userId);

            //when
            Long extractedUserId = authTokenCreator.extractPayload(authToken.accessToken());

            //then
            assertThat(extractedUserId).isEqualTo(userId);
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "invalid"})
        @DisplayName("실패: 비어있거나 잘못된 엑세스 토큰")
        void fail(String invalidAccessToken) {
            //given

            //when, then
            assertThatThrownBy(() -> authTokenCreator.extractPayload(invalidAccessToken))
                    .isInstanceOf(InvalidTokenException.class);
        }
    }
}
