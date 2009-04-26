package com.googlecode.teltra.config;

public final class PropertyRetrieverException extends RuntimeException {

    private static final long serialVersionUID = -8761309209533278712L;

    public PropertyRetrieverException(final String message) {
        super(message);
    }

    public PropertyRetrieverException(final Throwable cause) {
        super(cause);
    }


    public PropertyRetrieverException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
