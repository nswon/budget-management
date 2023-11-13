package com.budgetmanagement.budgetmanagement.expense.repository.impl;

import com.budgetmanagement.budgetmanagement.budget.domain.BudgetCategoryType;
import com.budgetmanagement.budgetmanagement.expense.dto.response.ExpenseCategoryTotalAmountResponse;
import com.budgetmanagement.budgetmanagement.expense.dto.response.ExpenseResponse;
import com.budgetmanagement.budgetmanagement.expense.repository.ExpenseQRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.budgetmanagement.budgetmanagement.expense.domain.QExpense.expense;

@Component
@RequiredArgsConstructor
public class ExpenseQRepositoryImpl implements ExpenseQRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public int getTotalAmount(Long userId, String category, int minAmount, int maxAmount) {
        return queryFactory
                .select(expense.amount.sum())
                .from(expense)
                .where(
                        equalsUser(userId),
                        expense.isExcluded.isFalse(),
                        filteringCategory(category),
                        filteringAmount(minAmount, maxAmount)

                )
                .fetchFirst();
    }

    @Override
    public List<ExpenseCategoryTotalAmountResponse> getCategoryTotalAmounts(Long userId, String category, int minAmount, int maxAmount) {
        return queryFactory
                .select(Projections.constructor(ExpenseCategoryTotalAmountResponse.class,
                        expense.amount.sum()
                ))
                .from(expense)
                .where(
                        equalsUser(userId),
                        filteringCategory(category),
                        filteringAmount(minAmount, maxAmount)
                )
                .groupBy(expense.category)
                .fetch();
    }

    public List<ExpenseResponse> getExpenses(Long userId, String category, int minAmount, int maxAmount) {
        return queryFactory
                .select(Projections.constructor(ExpenseResponse.class,
                        expense.date,
                        expense.amount,
                        expense.category
                ))
                .from(expense)
                .where(
                        equalsUser(userId),
                        filteringCategory(category),
                        filteringAmount(minAmount, maxAmount)
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

        BudgetCategoryType categoryType = BudgetCategoryType.toEnum(category);
        return expense.category.eq(categoryType);
    }

    private BooleanExpression filteringAmount(int minAmount, int maxAmount) {
        return expense.amount.goe(minAmount).and(expense.amount.loe(maxAmount));
    }
}
