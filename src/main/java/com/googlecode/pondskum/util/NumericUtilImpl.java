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

//TODO: move this to Pinthura.
public final class NumericUtilImpl implements NumericUtil {

    public boolean isNumber(final String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Integer getNumber(final String value) {
        return isNumber(value) ? Integer.parseInt(value) : 0;
    }

    @Override
    public boolean isRealNumber(final String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Double getRealNumber(final String value) {
        return isRealNumber(value) ? Double.parseDouble(value) : 0d;
    }
}
