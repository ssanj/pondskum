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
package com.googlecode.pondskum.util;

public interface NumericUtil {

    boolean isNumber(String value);

    /**
     * Returns the number specified by the value, or 0 if the value is not a number.
     * @param value The <code>String</code> value to convert into a number.
     * @return The value as a number or 0.
     */
    Integer getNumber(String value);

    /**
     * Returns the number specified by the value (if it is a number) or the defaultValue if not.
     * @param value The <code>String</code> value to convert into a number.
     * @param defaultValue The default to use if the value is not a number.
     * @return The value as a number or the defaultValue.
     */
    Integer getNumber(String value, int defaultValue);

    boolean isRealNumber(String value);

    Double getRealNumber(String value);

}
