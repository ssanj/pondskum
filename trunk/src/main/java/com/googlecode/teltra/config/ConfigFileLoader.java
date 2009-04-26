package com.googlecode.teltra.config;

import java.util.Properties;

public interface ConfigFileLoader {

    Properties loadProperties(String propertyName) throws ConfigFileLoaderException;
}
