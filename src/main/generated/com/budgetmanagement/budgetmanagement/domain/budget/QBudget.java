package com.budgetmanagement.budgetmanagement.domain.budget;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBudget is a Querydsl query type for Budget
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBudget extends EntityPathBase<Budget> {

    private static final long serialVersionUID = 1603292753L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBudget budget = new QBudget("budget");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final com.budgetmanagement.budgetmanagement.domain.category.QCategory category;

    public final ComparablePath<java.time.YearMonth> date = createComparable("date", java.time.YearMonth.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.budgetmanagement.budgetmanagement.domain.user.QUser user;

    public QBudget(String variable) {
        this(Budget.class, forVariable(variable), INITS);
    }

    public QBudget(Path<? extends Budget> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBudget(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBudget(PathMetadata metadata, PathInits inits) {
        this(Budget.class, metadata, inits);
    }

    public QBudget(Class<? extends Budget> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.budgetmanagement.budgetmanagement.domain.category.QCategory(forProperty("category")) : null;
        this.user = inits.isInitialized("user") ? new com.budgetmanagement.budgetmanagement.domain.user.QUser(forProperty("user")) : null;
    }

}

