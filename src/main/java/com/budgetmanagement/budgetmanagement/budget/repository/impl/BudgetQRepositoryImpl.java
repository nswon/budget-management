package com.budgetmanagement.budgetmanagement.budget.repository.impl;

import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetCategoryAmountResponse;
import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetRecommendResponse;
import com.budgetmanagement.budgetmanagement.budget.repository.BudgetQRepository;
import com.budgetmanagement.budgetmanagement.user.domain.User;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.budgetmanagement.budgetmanagement.budget.domain.QBudget.budget;

@Component
@RequiredArgsConstructor
public class BudgetQRepositoryImpl implements BudgetQRepository {
    private final JPAQueryFactory queryFactory;

    //총액으로 기존 유저들이 설정한 카테고리 별 예산을 통계하여 금액으로 추천하는 쿼리
    public List<BudgetRecommendResponse> getRecommendationBudget(int totalAmount, int userCount) {
        return queryFactory
                .select(Projections.constructor(BudgetRecommendResponse.class,
                        budget.category,
                        getCategoryAmountByTotalAmount(totalAmount, userCount)
                ))
                .from(budget)
                .groupBy(budget.category)
                .fetch();
    }

    //카테고리 평균 비율을 금액으로 바꿔서 반환하는 메서드
    private NumberExpression<Integer> getCategoryAmountByTotalAmount(int totalAmount, int userCount) {
        NumberExpression<Double> averageCategoryRatio = getAverageCategoryRatio(userCount);
        return averageCategoryRatio.divide(100).multiply(totalAmount).intValue();
    }

    //카테고리 평균 비율을 구하는 메서드
    private NumberExpression<Double> getAverageCategoryRatio(int userCount) {
        return budget.ratio.sum().doubleValue().divide(userCount);
    }

    //총액으로 카테고리 별 금액을 계산하는 쿼리
    @Override
    public List<BudgetCategoryAmountResponse> getCategoryAmounts(int totalAmount, User user) {
        return queryFactory
                .select(Projections.constructor(BudgetCategoryAmountResponse.class,
                        budget.category,
                        getCategoryAmount(totalAmount)
                ))
                .from(budget)
                .where(budget.user.id.eq(user.getId()))
                .fetch();
    }

    private NumberExpression<Integer> getCategoryAmount(int totalAmount) {
        NumberExpression<Integer> amount = budget.ratio.doubleValue().divide(100).multiply(totalAmount).intValue();
        return amount.divide(100).round().multiply(100);
    }
}
