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

import com.googlecode.pondskum.config.ConfigurationEnum;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.util.Properties;

public final class TimerDelayWithInvalidDataUnderTest {

    private static final int TEN_MINUTES        = 10;
    private static final int TEN_MINUTES_IN_MS  = 600000;

    @Test
    public void shouldReturnTenMinutesAsTheDelay() {
        assertThat(new TimerDelay(createInvalidConfiguration()).getFrequencyInMinutes(), equalTo(TEN_MINUTES));
    }

    @Test
    public void shouldReturnSixHundredThousandMilliSecondsAsTheDelay() {
        assertThat(new TimerDelay(createInvalidConfiguration()).getFrequencyInMilliSeconds(), equalTo(TEN_MINUTES_IN_MS));
    }

    private Properties createInvalidConfiguration() {
        Properties properties = new Properties();
        properties.put(ConfigurationEnum.UPDATE_INTERVAL.getKey(), "not_a_number");
        return properties;
    }
}
