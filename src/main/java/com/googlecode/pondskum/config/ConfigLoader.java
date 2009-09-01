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
 * Loads the configuration parameters that can be supplied to this API. Parameters will be read and returned as <code>Properties</code>
 * objects with key,value pairs.
 */
public interface ConfigLoader {

    /**
     * Returns the user-set parameters to this API as a <code>Properties</code> object.
     * @return The user-set parameters to this API as a <code>Properties</code> object.
     */
    Config loadConfig();
}
