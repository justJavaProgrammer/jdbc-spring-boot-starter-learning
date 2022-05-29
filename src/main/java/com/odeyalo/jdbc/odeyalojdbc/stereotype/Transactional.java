package com.odeyalo.jdbc.odeyalojdbc.stereotype;

import com.odeyalo.jdbc.odeyalojdbc.support.TransactionIsolationLevelType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {

    TransactionIsolationLevelType isolationLevel() default TransactionIsolationLevelType.TRANSACTION_READ_UNCOMMITTED;

}
