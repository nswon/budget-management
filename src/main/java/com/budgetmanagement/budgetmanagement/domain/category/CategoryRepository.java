package com.budgetmanagement.budgetmanagement.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Category c WHERE c.budget.id = :budgetId")
    void deleteAllBy(@Param("budgetId") Long budgetId);
}
