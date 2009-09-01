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
package com.googlecode.pondskum.timer;

import com.googlecode.pondskum.config.Config;
import com.googlecode.pondskum.util.NumericUtil;
import com.googlecode.pondskum.util.NumericUtilImpl;

public final class TimerDelay implements RepeatFrequency {

    /**
     * The minimum delay allowed is 10 minutes.
     */
    private static final int TEN_MINUTES = 10;

    private static final int MINUTE_IN_A_MILLISECOND = 60 * 1000;

    private int frequencyInMinutes;
    private NumericUtil numericUtil;

    public TimerDelay(final Config config) {
        numericUtil = new NumericUtilImpl();
        calculateFrequency(config);
    }

    private void calculateFrequency(final Config config) {
        String intervalProperty = config.getRepeatFrequencyInMinutes();
        frequencyInMinutes = getAMinimumOfTenMinutesForTheDelay(intervalProperty);
    }

    private int getAMinimumOfTenMinutesForTheDelay(final String intervalProperty) {
        return Math.max(TEN_MINUTES, numericUtil.getNumber(intervalProperty));
    }

    @Override
    public int getFrequencyInMinutes() {
        return frequencyInMinutes;
    }

    @Override
    public int getFrequencyInMilliSeconds() {
        return frequencyInMinutes * MINUTE_IN_A_MILLISECOND;
    }
}
