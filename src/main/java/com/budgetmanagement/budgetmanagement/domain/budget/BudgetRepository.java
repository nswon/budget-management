package com.budgetmanagement.budgetmanagement.domain.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.YearMonth;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Modifying
    @Query("DELETE FROM Budget b where b.date = :date")
    void deleteAllBy(@Param("date") YearMonth date);
}
