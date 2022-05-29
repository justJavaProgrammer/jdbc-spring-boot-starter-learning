package com.odeyalo.jdbc.odeyalojdbc.support;

import java.lang.reflect.Method;

public interface TransactionInvocationHandlerDelegate {

    Object invokeTransaction(Method method, Object originalObject, TransactionIsolationLevelType isolationLevel, Object[] args) throws Exception;

}
