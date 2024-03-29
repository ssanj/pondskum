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
package com.googlecode.pondskum.client;

public final class BigpondMonthlyUsageImpl implements BigpondMonthlyUsage {

    private final String month;
    private final BigpondUsage bigpondUsage;

    public BigpondMonthlyUsageImpl(final String month, final BigpondUsage bigpondUsage) {
        this.bigpondUsage = bigpondUsage;
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public BigpondUsage getBigpondUsage() {
        return bigpondUsage;
    }

    @Override
    public String toString() {
        return "BigpondMonthlyUsageImpl{" +
                "month='" + month + '\'' +
                ", bigpondUsage=" + bigpondUsage +
                '}';
    }
}
