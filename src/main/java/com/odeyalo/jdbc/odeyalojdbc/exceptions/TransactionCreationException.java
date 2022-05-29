package com.odeyalo.jdbc.odeyalojdbc.exceptions;

import java.sql.SQLException;

public class TransactionCreationException extends SQLException {
    public TransactionCreationException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public TransactionCreationException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public TransactionCreationException(String reason) {
        super(reason);
    }
}
