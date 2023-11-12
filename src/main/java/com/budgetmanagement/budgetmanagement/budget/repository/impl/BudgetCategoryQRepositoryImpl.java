package com.budgetmanagement.budgetmanagement.budget.repository.impl;

import com.budgetmanagement.budgetmanagement.budget.dto.response.BudgetRecommendationResponse;
import com.budgetmanagement.budgetmanagement.budget.repository.BudgetCategoryQRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.budgetmanagement.budgetmanagement.budget.domain.QBudgetCategory.budgetCategory;
import static com.budgetmanagement.budgetmanagement.user.domain.QUser.user;

@Component
@RequiredArgsConstructor
public class BudgetCategoryQRepositoryImpl implements BudgetCategoryQRepository {
    private final JPAQueryFactory queryFactory;

    //총액을 입력하면 기존 유저들이 설정한 카테고리 별 예산을 통계하여 금액으로 추천하는 쿼리
    public List<BudgetRecommendationResponse> getRecommendationBudget(int totalAmount, int userCount) {
        return queryFactory
                .select(Projections.constructor(BudgetRecommendationResponse.class,
                        budgetCategory.categoryType,
                        getCategoryAmountByTotalAmount(totalAmount, userCount)
                ))
                .from(budgetCategory)
                .groupBy(budgetCategory.categoryType)
                .fetch();
    }

    //카테고리 평균 비율을 금액으로 바꿔서 반환하는 메서드
    private NumberExpression<Integer> getCategoryAmountByTotalAmount(int totalAmount, int userCount) {
        NumberExpression<Double> averageCategoryRatio = getAverageCategoryRatio(userCount);
        return averageCategoryRatio.divide(100).multiply(totalAmount).intValue();
    }

    //카테고리 평균 비율을 구하는 메서드
    private NumberExpression<Double> getAverageCategoryRatio(int userCount) {
        return budgetCategory.ratio.sum().doubleValue().divide(userCount);
    }
}
