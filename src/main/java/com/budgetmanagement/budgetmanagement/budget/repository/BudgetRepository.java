package com.budgetmanagement.budgetmanagement.budget.repository;

import com.budgetmanagement.budgetmanagement.budget.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Budget b WHERE b.user.id = :userId")
    void deleteAllByUserInBatch(@Param("userId") Long userId);
}
