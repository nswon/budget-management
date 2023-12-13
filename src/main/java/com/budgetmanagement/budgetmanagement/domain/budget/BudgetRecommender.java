package com.budgetmanagement.budgetmanagement.domain.budget;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BudgetRecommender {
    private final BudgetQueryRepository budgetQueryRepository;

    public BudgetRecommender(BudgetQueryRepository budgetQueryRepository) {
        this.budgetQueryRepository = budgetQueryRepository;
    }

    public List<BudgetContent> recommend(BudgetAmount amount) {
        return budgetQueryRepository.getList(amount);
    }
}
