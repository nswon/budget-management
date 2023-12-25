package com.budgetmanagement.budgetmanagement.domain.budget;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.budgetmanagement.budgetmanagement.domain.budget.QBudget.budget;
import static com.budgetmanagement.budgetmanagement.domain.category.QCategory.category;

@Component
public class BudgetQueryRepositoryImpl implements BudgetQueryRepository {
    private final JPAQueryFactory factory;

    public BudgetQueryRepositoryImpl(JPAQueryFactory factory) {
        this.factory = factory;
    }

    //예산 금액으로 기존 유저들이 설정한 카테고리 별 예산을 통계하여 금액으로 변경하여 반환
    @Override
    public List<BudgetContent> getList(BudgetAmount amount) {
        int totalAmount = factory
                .select(budget.amount.sum())
                .from(budget)
                .fetchFirst();

        return factory
                .select(Projections.constructor(BudgetContent.class,
                        budget.category.name,
                        getAverageAmount(totalAmount, amount)
                ))
                .from(budget)
                .join(budget.category, category)
                .groupBy(budget.category.name)
                .fetch();
    }

    private NumberExpression<Integer> getAverageAmount(int totalAmount, BudgetAmount amount) {
        NumberExpression<Double> ratio = calculateAverageRatio(totalAmount);
        return ratio.multiply(amount.amount()).intValue();
    }

    private NumberExpression<Double> calculateAverageRatio(int totalAmount) {
        return budget.amount.sum().doubleValue().divide(totalAmount);
    }
}
