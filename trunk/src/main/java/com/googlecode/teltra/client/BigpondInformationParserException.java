package com.googlecode.teltra.client;

public final class BigpondInformationParserException extends RuntimeException {

    private static final long serialVersionUID = 3719104279351756042L;

    public BigpondInformationParserException(final String message) {
        super(message);
    }

    public BigpondInformationParserException(final Throwable cause) {
        super(cause);
    }


    public BigpondInformationParserException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
