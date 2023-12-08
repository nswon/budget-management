package com.budgetmanagement.budgetmanagement.domain.user;

import com.budgetmanagement.budgetmanagement.support.error.ApiException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.budgetmanagement.budgetmanagement.support.error.ErrorType.NOT_FOUND;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByAccount(String account);
    Optional<User> findByAccount(String account);

    default User getByAccount(String account) {
        return findByAccount(account)
                .orElseThrow(() -> new ApiException(NOT_FOUND));
    }

    default User getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new ApiException(NOT_FOUND));
    }
}
