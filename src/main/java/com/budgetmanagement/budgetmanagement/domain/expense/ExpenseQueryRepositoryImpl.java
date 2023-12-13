package com.budgetmanagement.budgetmanagement.domain.expense;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.budgetmanagement.budgetmanagement.domain.expense.QExpense.expense;

@Component
@RequiredArgsConstructor
public class ExpenseQueryRepositoryImpl implements ExpenseQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public int getTotalAmount(long userId, String category, ExpenseRange range) {
        return queryFactory
                .select(expense.amount.sum())
                .from(expense)
                .where(
                        equalsUser(userId),
                        expense.isExcluded.isFalse(),
                        filteringCategory(category),
                        filteringRange(range)
                )
                .fetchFirst();
    }

    //카테고리별 지출 합계를 반환
    @Override
    public List<CategoryExpense> getCategoryTotalList(long userId, String category, ExpenseRange range) {
        return queryFactory
                .select(Projections.constructor(CategoryExpense.class,
                        expense.amount.sum()
                ))
                .from(expense)
                .where(
                        equalsUser(userId),
                        filteringCategory(category),
                        filteringRange(range)
                )
                .groupBy(expense.category)
                .fetch();
    }

    @Override
    public List<ExpenseSummary> getExpenses(long userId, String category, ExpenseRange range) {
        return queryFactory
                .select(Projections.constructor(ExpenseSummary.class,
                        expense.date,
                        expense.amount,
                        expense.category.name
                ))
                .from(expense)
                .where(
                        equalsUser(userId),
                        filteringCategory(category),
                        filteringRange(range)
                )
                .orderBy(expense.date.desc())
                .fetch();
    }

    private BooleanExpression equalsUser(Long userId) {
        return expense.user.id.eq(userId);
    }

    private BooleanExpression filteringCategory(String category) {
        if(category == null) {
            return null;
        }

        return expense.category.name.eq(category);
    }

    private BooleanExpression filteringRange(ExpenseRange range) {
        return expense.amount.goe(range.minAmount()).and(expense.amount.loe(range.maxAmount()));
    }
}
