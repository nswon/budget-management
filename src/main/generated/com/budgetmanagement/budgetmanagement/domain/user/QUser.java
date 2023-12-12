package com.budgetmanagement.budgetmanagement.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1006607919L;

    public static final QUser user = new QUser("user");

    public final StringPath account = createString("account");

    public final ListPath<com.budgetmanagement.budgetmanagement.domain.budget.Budget, com.budgetmanagement.budgetmanagement.domain.budget.QBudget> budgets = this.<com.budgetmanagement.budgetmanagement.domain.budget.Budget, com.budgetmanagement.budgetmanagement.domain.budget.QBudget>createList("budgets", com.budgetmanagement.budgetmanagement.domain.budget.Budget.class, com.budgetmanagement.budgetmanagement.domain.budget.QBudget.class, PathInits.DIRECT2);

    public final ListPath<com.budgetmanagement.budgetmanagement.domain.expense.Expense, com.budgetmanagement.budgetmanagement.domain.expense.QExpense> expenses = this.<com.budgetmanagement.budgetmanagement.domain.expense.Expense, com.budgetmanagement.budgetmanagement.domain.expense.QExpense>createList("expenses", com.budgetmanagement.budgetmanagement.domain.expense.Expense.class, com.budgetmanagement.budgetmanagement.domain.expense.QExpense.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath password = createString("password");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

