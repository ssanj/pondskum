package com.googlecode.teltra.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigFileLoaderImpl implements ConfigFileLoader {

    private static final String CONFIG_FILE_NAME = "bigpond_config.properties";

    private final PropertyRetriever propertyRetriever;

    public ConfigFileLoaderImpl(final PropertyRetriever propertyRetriever) {
        this.propertyRetriever = propertyRetriever;
    }

    public Properties loadProperties(final String propertyName) throws ConfigFileLoaderException {
        Properties properties;
        try {
            String propertyValue = propertyRetriever.retrieveProperty(propertyName);
            String configFile = new StringBuilder().
                                        append(propertyValue).
                                        append(File.separator).
                                        append(CONFIG_FILE_NAME).
                                        toString();

            InputStream in = new FileInputStream(configFile);
            properties = new Properties();
            properties.load(in);
            in.close();
        } catch (Exception e) {
            throw new ConfigFileLoaderException(e);
        }

        return properties;
    }

}
