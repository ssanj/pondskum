package com.googlecode.teltra.config;

public interface PropertyRetriever {

    String retrieveProperty(String propertyName) throws PropertyRetrieverException;
}
