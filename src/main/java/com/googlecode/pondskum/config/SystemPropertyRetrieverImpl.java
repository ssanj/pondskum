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

    @Override
    public String getLineSeparator() throws PropertyRetrieverException {
        return retrieveProperty("line.separator");
    }

    @Override
    public String getFileSeparator() throws PropertyRetrieverException {
        return retrieveProperty("file.separator");
    }
}
