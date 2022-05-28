package com.odeyalo.jdbc.odeyalojdbc.exceptions;

import java.sql.SQLException;

public class ConnectionException extends SQLException {
    public ConnectionException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public ConnectionException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public ConnectionException(String reason) {
        super(reason);
    }
}
