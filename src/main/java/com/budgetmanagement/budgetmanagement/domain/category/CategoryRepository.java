package com.budgetmanagement.budgetmanagement.domain.category;

import com.budgetmanagement.budgetmanagement.support.error.ApiException;
import com.budgetmanagement.budgetmanagement.support.error.ErrorType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    default Category getByName(String name) {
        return findByName(name)
                .orElseThrow(() -> new ApiException(ErrorType.NOT_FOUND));
    }
}
