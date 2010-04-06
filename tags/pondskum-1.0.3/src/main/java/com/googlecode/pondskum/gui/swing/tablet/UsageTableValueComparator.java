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
package com.googlecode.pondskum.gui.swing.tablet;

import com.googlecode.pondskum.util.NumericUtil;

import java.util.Comparator;

public final class UsageTableValueComparator implements Comparator<UsageTableValue> {

    private final NumericUtil numericUtil;

    public UsageTableValueComparator(final NumericUtil numericUtil) {
        this.numericUtil = numericUtil;
    }

    @Override public int compare(final UsageTableValue v1, final UsageTableValue v2) {
        String value1 = v1.getValue();
        String value2 = v2.getValue();

        char sizeUnits1 = getSizeUnits(value1);
        char sizeUnits2 = getSizeUnits(value2);
        if (sizeUnits1 != sizeUnits2) {
            return (sizeUnits1 - sizeUnits2) * -1; //G is before M but > M for our purposes.
        }

        return ((Double) Math.signum(getUsage(value1) - getUsage(value2))).intValue();
    }

    private double getUsage(final String value) {
        return numericUtil.getRealNumber(value.substring(0, value.length() - 2));
    }

    private char getSizeUnits(final String value) {
        return value.charAt(value.length() - 1);
    }
}
