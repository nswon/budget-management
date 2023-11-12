package com.budgetmanagement.budgetmanagement.user.repository;

import com.budgetmanagement.budgetmanagement.user.domain.User;
import com.budgetmanagement.budgetmanagement.user.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByAccount(String account);
    Optional<User> findByAccount(String account);

    default User getByAccount(String account) {
        return findByAccount(account)
                .orElseThrow(UserNotFoundException::new);
    }

    default User getById(Long id) {
        return findById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
