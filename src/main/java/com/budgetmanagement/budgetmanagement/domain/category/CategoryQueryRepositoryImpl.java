package com.budgetmanagement.budgetmanagement.domain.category;

import com.budgetmanagement.budgetmanagement.domain.budget.BudgetAmount;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.budgetmanagement.budgetmanagement.domain.budget.QBudget.budget;
import static com.budgetmanagement.budgetmanagement.domain.category.QCategory.category;

@Component
public class CategoryQueryRepositoryImpl implements CategoryQueryRepository {
    private final JPAQueryFactory factory;

    public CategoryQueryRepositoryImpl(JPAQueryFactory factory) {
        this.factory = factory;
    }

    //예산 금액으로 기존 유저들이 설정한 카테고리 별 예산을 통계하여 금액으로 변경하여 반환
    @Override
    public List<CategoryBudget> getList(BudgetAmount amount) {
        return factory
                .select(Projections.constructor(CategoryBudget.class,
                        category.name,
                        getAverageAmount(amount)
                ))
                .from(category)
                .join(category.budget, budget)
                .groupBy(category.name)
                .fetch();
    }

    private NumberExpression<Integer> getAverageAmount(BudgetAmount amount) {
        NumberExpression<Double> ratio = calculateAverageRatio();
        return ratio.divide(100).multiply(amount.amount()).intValue();
    }

    private NumberExpression<Double> calculateAverageRatio() {
        return category.amount.sum().doubleValue().divide(budget.amount.sum()).multiply(100);
    }
}