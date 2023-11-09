package com.budgetmanagement.budgetmanagement.auth.application;

import com.budgetmanagement.budgetmanagement.auth.exception.ExpiredTokenException;
import com.budgetmanagement.budgetmanagement.auth.exception.InvalidTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class JwtTokenProviderTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    private final String payload = "1";

    @Test
    @DisplayName("엑세스 토큰을 생성한다.")
    void createAccessToken() {
        //given

        //when
        String accessToken = jwtTokenProvider.createAccessToken(payload);

        //then
        assertThat(accessToken.split("\\.")).hasSize(3);
    }

    @Test
    @DisplayName("리프래쉬 토큰을 생성한다")
    void createRefreshToken() {
        //given

        //when
        String refreshToken = jwtTokenProvider.createRefreshToken(payload);

        //then
        assertThat(refreshToken.split("\\.")).hasSize(3);
    }

    @Nested
    @DisplayName("토큰을 검증한다.")
    class validateToken {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            String accessToken = jwtTokenProvider.createAccessToken(payload);

            //when, then
            jwtTokenProvider.validateToken(accessToken);
        }

        @Test
        @DisplayName("실패: 만료된 토큰")
        void fail() {
            //given
            String secretKey = "a".repeat(32);
            JwtTokenProvider expiredJwtTokenProvider = new JwtTokenProvider(
                    secretKey,0, 0
            );
            String accessToken = expiredJwtTokenProvider.createAccessToken(payload);

            //when, then
            assertThatThrownBy(() -> expiredJwtTokenProvider.validateToken(accessToken))
                    .isInstanceOf(ExpiredTokenException.class);
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "invalid"})
        @DisplayName("실패: 비어있거나 잘못된 토큰")
        void fail2(String invalidToken) {
            //given

            //when, then
            assertThatThrownBy(() -> jwtTokenProvider.validateToken(invalidToken))
                    .isInstanceOf(InvalidTokenException.class);
        }
    }

    @Test
    @DisplayName("페이로드를 추출한다")
    void getPayload() {
        //given
        String accessToken = jwtTokenProvider.createAccessToken(payload);

        //when
        String extractPayload = jwtTokenProvider.getPayload(accessToken);

        //then
        assertThat(extractPayload).isEqualTo(payload);
    }
}
