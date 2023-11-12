package com.budgetmanagement.budgetmanagement.budget.repository;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategory;
import com.budgetmanagement.budgetmanagement.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Long> {
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM BudgetCategory b WHERE b.user.id = :userId")
    void deleteAllByUserInBatch(@Param("userId") Long userId);
}
