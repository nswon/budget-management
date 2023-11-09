package com.budgetmanagement.budgetmanagement.user.repository;

import com.budgetmanagement.budgetmanagement.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByAccount(String account);
}
