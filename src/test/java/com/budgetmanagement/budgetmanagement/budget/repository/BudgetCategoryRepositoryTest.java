package com.budgetmanagement.budgetmanagement.budget.repository;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategory;
import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategoryType;
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
public class BudgetCategoryRepositoryTest {

    @Autowired
    private BudgetCategoryRepository budgetCategoryRepository;

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

        BudgetCategory shoppingCategory = BudgetCategory.builder()
                .user(user)
                .categoryType(BudgetCategoryType.SHOPPING)
                .amount(10000)
                .ratio(50)
                .build();
        BudgetCategory financeCategory = BudgetCategory.builder()
                .user(user)
                .categoryType(BudgetCategoryType.FINANCE)
                .amount(10000)
                .ratio(50)
                .build();
        List<BudgetCategory> categories = List.of(shoppingCategory, financeCategory);
        budgetCategoryRepository.saveAll(categories);

        //when
        budgetCategoryRepository.deleteAllByUserInBatch(user.getId());

        //then
        User foundUser = userRepository.getById(user.getId());

        assertAll(() -> {
            assertThat(budgetCategoryRepository.findAll()).isEmpty();
            assertThat(foundUser.getBudgetCategories()).isEmpty();
        });
    }
}
