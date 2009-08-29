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

import static com.googlecode.pondskum.config.ConfigurationEnum.UPDATE_INTERVAL;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.util.Properties;

public final class TimerDelayUnderTest {

    @Test
    public void shouldReturnItsFrequencyInMinutesForADelay() {
        RepeatFrequency timerDelay = new TimerDelay(createConfigurationPropertiesWithDelayOf("30"));
        assertThat(timerDelay.getFrequencyInMinutes(), equalTo(30));
    }

    @Test
    public void shouldReturnItsFrequencyInMinutesForAnotherDelay() {
        RepeatFrequency timerDelay = new TimerDelay(createConfigurationPropertiesWithDelayOf("45"));
        assertThat(timerDelay.getFrequencyInMinutes(), equalTo(45));
    }

    @Test
    public void shouldReturnTenMinutesIfTheDelaySuppliedIsLessThanTenMinutes() {
        TimerDelay timerDelay = new TimerDelay(createConfigurationPropertiesWithDelayOf("9"));
        assertThat(timerDelay.getFrequencyInMinutes(), equalTo(10));
    }

    @Test
    public void shouldReturnItsFrequencyInMilliSecondsForADelay() {
        RepeatFrequency timerDelay = new TimerDelay(createConfigurationPropertiesWithDelayOf("45"));
        assertThat(timerDelay.getFrequencyInMilliSeconds(), equalTo(2700000));
    }

    @Test
    public void shouldReturnItsFrequencyInMiliiSecondsForAnotherDelay() {
        RepeatFrequency timerDelay = new TimerDelay(createConfigurationPropertiesWithDelayOf("30"));
        assertThat(timerDelay.getFrequencyInMilliSeconds(), equalTo(1800000));
    }

    private Properties createConfigurationPropertiesWithDelayOf(final String delayInMinutes) {
        Properties properties = new Properties();
        properties.put(UPDATE_INTERVAL.getKey(), delayInMinutes);
        return properties;
    }
}
