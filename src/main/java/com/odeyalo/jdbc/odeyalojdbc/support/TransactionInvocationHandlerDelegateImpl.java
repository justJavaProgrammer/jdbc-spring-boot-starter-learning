package com.odeyalo.jdbc.odeyalojdbc.support;

import com.odeyalo.jdbc.odeyalojdbc.exceptions.TransactionCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class TransactionInvocationHandlerDelegateImpl implements TransactionInvocationHandlerDelegate {
    private final Connection connection;
    private final Logger logger = LoggerFactory.getLogger(TransactionInvocationHandlerDelegateImpl.class);

    @Autowired
    public TransactionInvocationHandlerDelegateImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Object invokeTransaction(Method method, Object originalObject, TransactionIsolationLevelType isolationLevel, Object[] args) throws TransactionCreationException {
        try {
            int level = isolationLevel.getIsolationLevel();
            this.connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            this.connection.setAutoCommit(false);
            Object invoke = method.invoke(originalObject, args);
            this.connection.commit();
            return invoke;
        } catch (Exception ex) {
            try {
                this.logger.error("TRANSACTION FAILED. STARTING ROLLBACK PROCESS...");
                this.logger.error("EXCEPTION STACKTRACE: ", ex);
                connection.rollback();
                this.logger.info("======= SUCCESSFUL ROLLBACK DATABASE =======");
            } catch (SQLException e) {
                this.logger.error("Transaction rollback was failed. Message: ", e);
                throw new TransactionCreationException(e.getMessage(), e.getSQLState(), e.getErrorCode());
            }
        }
        return originalObject;
    }
}
