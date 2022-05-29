package com.odeyalo.jdbc.odeyalojdbc.support;

import java.sql.Connection;

public enum TransactionIsolationLevelType {
    TRANSACTION_READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
    TRANSACTION_READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
    TRANSACTION_REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
    TRANSACTION_SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE),
    TRANSACTION_NONE(Connection.TRANSACTION_NONE);

    private int isolationLevel;

    TransactionIsolationLevelType(int isolationLevel) {
        this.isolationLevel = isolationLevel;
    }

    public int getIsolationLevel() {
        return isolationLevel;
    }
}
