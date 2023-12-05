package com.budgetmanagement.budgetmanagement.auth.repository;

import com.budgetmanagement.budgetmanagement.auth.domain.AuthTokenRepository;
import com.budgetmanagement.budgetmanagement.auth.domain.AuthTokenRepositoryImpl;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AuthTokenRepositoryTest {
    private final Map<Long, String> tokenRepository = ExpiringMap.builder()
            .maxSize(1000)
            .expirationPolicy(ExpirationPolicy.CREATED)
            .expiration(15,TimeUnit.DAYS)
            .build();

    private final AuthTokenRepository authTokenRepository = new AuthTokenRepositoryImpl(tokenRepository);

    @Test
    @DisplayName("토큰을 저장한다.")
    void save() {
        //given
        Long userId = 1L;
        String refreshToken = "refresh";

        //when
        authTokenRepository.save(userId, refreshToken);

        //then
        String actual = authTokenRepository.findByUserId(userId)
                        .orElseThrow(() -> new IllegalArgumentException("테스트에 실패했습니다."));
        assertThat(actual).isEqualTo(refreshToken);
    }

    @Test
    @DisplayName("유저 id로 토큰을 가져온다.")
    void findByUserId() {
        //given
        Long userId = 1L;
        String refreshToken = "refresh";
        authTokenRepository.save(userId, refreshToken);

        //when
        String actual = authTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("테스트에 실패했습니다."));

        //then
        assertThat(actual).isEqualTo(refreshToken);
    }

    @Test
    @DisplayName("일정시간이 지나면 토큰이 삭제된다.")
    void expireToken() {
        //given
        Map<Long, String> tokenRepository = ExpiringMap.builder()
                .maxSize(1000)
                .expirationPolicy(ExpirationPolicy.CREATED)
                .expiration(0,TimeUnit.MILLISECONDS)
                .build();

        AuthTokenRepository expiredAuthTokenRepository = new AuthTokenRepositoryImpl(tokenRepository);

        //when
        Long userId = 1L;
        String refreshToken = "refresh";
        expiredAuthTokenRepository.save(userId, refreshToken);

        //then
        //억지로 optoinal에서 값을 꺼내는 경우 NoSuchElementException 발생
        assertThatThrownBy(() -> authTokenRepository.findByUserId(userId).get())
                .isInstanceOf(NoSuchElementException.class);
    }
}
