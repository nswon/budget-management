package com.budgetmanagement.budgetmanagement.user.repository;

import com.budgetmanagement.budgetmanagement.user.domain.User;
import com.budgetmanagement.budgetmanagement.user.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Nested
    @DisplayName("계정으로 유저가 존재하는지 확인한다.")
    class existsByAccount {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            User user = User.builder()
                    .account("account")
                    .password("password")
                    .build();
            userRepository.save(user);

            //when
            boolean hasUser = userRepository.existsByAccount("account");

            //then
            assertThat(hasUser).isTrue();
        }

        @Test
        @DisplayName("성공: 유저가 존재하지 않는다면 false로 반환된다.")
        void success2() {
            //given

            //when
            boolean hasUser = userRepository.existsByAccount("account");

            //then
            assertThat(hasUser).isFalse();
        }
    }
}
