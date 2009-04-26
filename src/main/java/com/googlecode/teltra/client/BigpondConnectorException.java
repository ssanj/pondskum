package com.googlecode.teltra.client;

public final class BigpondConnectorException extends RuntimeException {

    private static final long serialVersionUID = -2661675827939490217L;

    public BigpondConnectorException(final String message) {
        super(message);
    }

    public BigpondConnectorException(final Throwable cause) {
        super(cause);
    }


    public BigpondConnectorException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
