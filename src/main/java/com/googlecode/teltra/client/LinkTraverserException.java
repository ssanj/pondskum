package com.googlecode.teltra.client;

public final class LinkTraverserException extends RuntimeException {

    private static final long serialVersionUID = 3895293435902412463L;

    public LinkTraverserException(final String message) {
        super(message);
    }

    public LinkTraverserException(final Throwable cause) {
        super(cause);
    }


    public LinkTraverserException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
