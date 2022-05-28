package com.odeyalo.jdbc.odeyalojdbc.exceptions;

public class InitializeDriverClassException extends RuntimeException {
    public InitializeDriverClassException() {
        super();
    }

    public InitializeDriverClassException(String message) {
        super(message);
    }

    public InitializeDriverClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
