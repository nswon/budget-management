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
    public int getTotalAmount(Long userId, String category, ExpenseRange range) {
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

    @Override
    public List<CategoryTotal> getCategoryTotalList(Long userId, String category, ExpenseRange range) {
        return queryFactory
                .select(Projections.constructor(CategoryTotal.class,
                        expense.amount.sum()
                ))
                .from(expense)
                .where(
                        equalsUser(userId),
                        filteringCategory(category),
                        filteringRange(range)
                )
                .groupBy(expense)
                .fetch();
    }

    @Override
    public List<ExpenseContent> getExpenses(Long userId, String category, ExpenseRange range) {
        return queryFactory
                .select(Projections.constructor(ExpenseContent.class,
                        expense.date,
                        expense.amount,
                        expense.category,
                        null,
                        null
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

        return expense.category.eq(category);
    }

    private BooleanExpression filteringRange(ExpenseRange range) {
        return expense.amount.goe(range.minAmount()).and(expense.amount.loe(range.maxAmount()));
    }
}
