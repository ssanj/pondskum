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

import com.googlecode.pondskum.logger.LogProvider;

/**
 * Contains the user-specified configuration for connecting to their Bigpond account.
 */
public interface Config {

    /**
     * Returns a <code>LogProvider</code> to use for logging.
     * @return A <code>LogProvider</code> to use for logging.
     */
    LogProvider getLogProvider();

    /**
     * Bigpond username.
     * @return Bigpond username.
     */
    String getUsername();

    /**
     * Bigpond password.
     * @return Bigpond password.
     */
    String getPassword();

    /**
     * The file path to write usage data to.
     * @return The file path to write usage data to.
     */
    String getUsageDataFilePath();

    /**
     * Returns true if logging has been requested, false if not.
     * @return True if logging has been requested, false if not.
     */
    boolean isLoggingRequested();

    /**
     * The frequency (in minutes) in which to repeat information lookup.
     * @return The frequency (in minutes) in which to repeat information lookup
     */
    String getRepeatFrequencyInMinutes();
}
