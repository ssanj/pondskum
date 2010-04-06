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

import com.googlecode.pondskum.gui.swing.tablet.UsageTableValue;

public final class DefaultUsageConverter implements UsageConverter {

    private final NumericUtil numericUtil;

    public DefaultUsageConverter(final NumericUtil numericUtil) {
        this.numericUtil = numericUtil;
    }

    @Override public String toString(final String usageValue) {
        return toUsageTableValue(usageValue).getValue();
    }

    @Override public UsageTableValue toUsageTableValue(final String usageValue) {
        return new UsageTableValue(numericUtil.getNumber(usageValue));
    }
}
