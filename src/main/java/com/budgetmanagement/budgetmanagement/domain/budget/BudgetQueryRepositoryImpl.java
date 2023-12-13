package com.budgetmanagement.budgetmanagement.domain.budget;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.budgetmanagement.budgetmanagement.domain.budget.QBudget.budget;
import static com.budgetmanagement.budgetmanagement.domain.category.QCategory.category;
import static com.budgetmanagement.budgetmanagement.domain.user.QUser.user;

@Component
public class BudgetQueryRepositoryImpl implements BudgetQueryRepository {
    private final JPAQueryFactory factory;

    public BudgetQueryRepositoryImpl(JPAQueryFactory factory) {
        this.factory = factory;
    }

    //예산 금액으로 기존 유저들이 설정한 카테고리 별 예산을 통계하여 금액으로 변경하여 반환
    @Override
    public List<BudgetContent> getList(BudgetAmount amount) {
        return factory
                .select(Projections.constructor(BudgetContent.class,
                        budget.category.name,
                        getAverageAmount(amount)
                ))
                .from(budget)
                .join(budget.user, user)
                .join(budget.category, category)
                .groupBy(budget)
                .fetch();
    }

    private NumberExpression<Integer> getAverageAmount(BudgetAmount amount) {
        NumberExpression<Double> ratio = calculateAverageRatio();
        return ratio.divide(100).multiply(amount.amount()).intValue();
    }

    private NumberExpression<Double> calculateAverageRatio() {
        return budget.ratio.sum().divide(user.count());
    }
}
