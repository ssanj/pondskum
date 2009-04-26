package com.googlecode.teltra.client;

public final class FormSubmitterException extends RuntimeException {

    private static final long serialVersionUID = 375719663181928008L;

    public FormSubmitterException(final String message) {
        super(message);
    }

    public FormSubmitterException(final Throwable cause) {
        super(cause);
    }


    public FormSubmitterException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
