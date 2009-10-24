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
package com.googlecode.pondskum.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides a <code>Logger</code> given a class to log and an initial log level.
 */
public interface LogProvider {


    /**
     * Provides a <code>Logger</code> given a class to log and an initial log level.
     * @param loggerClass The class to log for.
     * @param logLevel The initial level
     * @return A <code>Logger</code> for the class supplied at the level requested.
     */
    Logger provide(Class<?> loggerClass, Level logLevel);

    /**
     * Provides a <code>Logger</code> given a class with a default logging level of {@link Level.INFO}.
     * @param loggerClass The class to log for.
     * @return A <code>Logger</code> for the clas supplied at the default logging level of {@link Level.INFO}.
     */
    Logger provide(Class<?> loggerClass);
}
