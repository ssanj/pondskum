package com.googlecode.teltra.config;

public final class SystemPropertyRetrieverImpl implements PropertyRetriever {

    public String retrieveProperty(final String propertyName) {
        String propertyValue = System.getProperty(propertyName);
        if (propertyValue == null || propertyValue.isEmpty()) {
            throw new PropertyRetrieverException(
                    new StringBuilder().append("System property " ).append(propertyName).append(" has not been set.").
                            append("Set it with -D").append(propertyName).append("=your_value").
                            toString());
        }

        return propertyValue;
    }
}
