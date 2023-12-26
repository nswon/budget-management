package com.budgetmanagement.budgetmanagement.domain.expense;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExpense is a Querydsl query type for Expense
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExpense extends EntityPathBase<Expense> {

    private static final long serialVersionUID = -889952251L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExpense expense = new QExpense("expense");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final com.budgetmanagement.budgetmanagement.domain.category.QCategory category;

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isExcluded = createBoolean("isExcluded");

    public final StringPath memo = createString("memo");

    public final com.budgetmanagement.budgetmanagement.domain.user.QUser user;

    public QExpense(String variable) {
        this(Expense.class, forVariable(variable), INITS);
    }

    public QExpense(Path<? extends Expense> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExpense(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExpense(PathMetadata metadata, PathInits inits) {
        this(Expense.class, metadata, inits);
    }

    public QExpense(Class<? extends Expense> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.budgetmanagement.budgetmanagement.domain.category.QCategory(forProperty("category")) : null;
        this.user = inits.isInitialized("user") ? new com.budgetmanagement.budgetmanagement.domain.user.QUser(forProperty("user")) : null;
    }

}

