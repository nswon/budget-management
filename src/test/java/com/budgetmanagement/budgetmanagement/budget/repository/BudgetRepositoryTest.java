package com.budgetmanagement.budgetmanagement.budget.repository;

import com.budgetmanagement.budgetmanagement.budget.domain.Budget;
import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategory;
import com.budgetmanagement.budgetmanagement.user.domain.User;
import com.budgetmanagement.budgetmanagement.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class BudgetRepositoryTest {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저로 유저의 카테고리들을 한번에 삭제한다.")
    void deleteByUserInBatch() {
        //given
        User user = User.builder()
                .account("account")
                .password("password")
                .build();
        userRepository.save(user);

        Budget shoppingCategory = Budget.builder()
                .user(user)
                .category(BudgetCategory.SHOPPING)
                .amount(10000)
                .ratio(50)
                .build();
        Budget financeCategory = Budget.builder()
                .user(user)
                .category(BudgetCategory.FINANCE)
                .amount(10000)
                .ratio(50)
                .build();
        List<Budget> categories = List.of(shoppingCategory, financeCategory);
        budgetRepository.saveAll(categories);

        //when
        budgetRepository.deleteAllByUserInBatch(user.getId());

        //then
        User foundUser = userRepository.getById(user.getId());

        assertAll(() -> {
            assertThat(budgetRepository.findAll()).isEmpty();
            assertThat(foundUser.getBudgets()).isEmpty();
        });
    }
}
