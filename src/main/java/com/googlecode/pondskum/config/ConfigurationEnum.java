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

/**
 * Defines system properties and property keys to configure the project.
 */
public enum ConfigurationEnum {

    /**
     * Defines how often connection information is updated.
     */
    UPDATE_INTERVAL("update.interval"),

    /**
     * Defines the location of the log file to write debug information to.
     */
    LOG_FILE("log"),

    /**
     * Defines the name of the system property that holds the location of the configuration file.
     */
    CONFIG_FILE_LOCATION("bigpond.config.location");

    private String key;

    ConfigurationEnum(final String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
