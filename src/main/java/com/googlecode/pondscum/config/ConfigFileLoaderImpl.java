/*
 * Copyright 2008 Sanjiv Sahayam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
