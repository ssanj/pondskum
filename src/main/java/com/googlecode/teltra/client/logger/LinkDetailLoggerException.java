package com.googlecode.teltra.client.logger;

public final class LinkDetailLoggerException extends RuntimeException {

    private static final long serialVersionUID = 3839522759978247399L;

    public LinkDetailLoggerException(final String message) {
        super(message);
    }

    public LinkDetailLoggerException(final Throwable cause) {
        super(cause);
    }


    public LinkDetailLoggerException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
