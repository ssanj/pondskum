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
package com.googlecode.pondskum.config;

import com.googlecode.pondskum.logger.DefaultLogProvider;
import com.googlecode.pondskum.logger.LogProvider;

import java.io.File;
import java.util.Properties;

public final class DefaultConfig implements Config {

    private static final String TEMP_FILE_NAME = "usage_data.html";

    private final Properties configProperties;
    private final LogProvider logProvider;

    public DefaultConfig(final Properties configProperties) {
        this.configProperties = configProperties;
        logProvider = new DefaultLogProvider(getPropertyValue(ConfigurationEnum.LOG_FILE));
    }

    public DefaultConfig(final Properties configProperties, final LogProvider logProvider) {
        this.configProperties = configProperties;
        this.logProvider = logProvider;
    }

    @Override
    public LogProvider getLogProvider() {
        return logProvider;
    }

    @Override
    public String getUsername() {
        return getPropertyValue(ConfigurationEnum.USERNAME);
    }

    @Override
    public String getPassword() {
        return getPropertyValue(ConfigurationEnum.PASSWORD);
    }

    @Override
    public String getUsageDataFilePath() {
       return new StringBuilder().append(getPropertyValue(ConfigurationEnum.TEMP_DIR)).
                append(File.separator).
                append(TEMP_FILE_NAME).
                toString();
    }

    @Override
    public boolean isLoggingRequested() {
        String logFileName = getPropertyValue(ConfigurationEnum.LOG_FILE);
        return (logFileName != null && !logFileName.trim().isEmpty());
    }

    @Override
    public String getRepeatFrequencyInMinutes() {
        return getPropertyValue(ConfigurationEnum.UPDATE_INTERVAL);
    }

    private String getPropertyValue(final ConfigurationEnum enumerationKey) {
        return configProperties.getProperty(enumerationKey.getKey());
    }
}
