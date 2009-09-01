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
import org.easymock.EasyMock;
import org.easymock.internal.MocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class TimerDelayWithInvalidDataUnderTest {

    private static final int TEN_MINUTES        = 10;
    private static final int TEN_MINUTES_IN_MS  = 600000;

    private MocksControl mocksControl;
    private Config mockConfig;

    @Before
    public void setup() {
        mocksControl = new MocksControl(MocksControl.MockType.DEFAULT);
        mockConfig = mocksControl.createMock(Config.class);
    }

    @Test
    public void shouldReturnTenMinutesAsTheDelay() {
        primeConfigWithInvalidDelay();
        mocksControl.replay();

        assertThat(new TimerDelay(mockConfig).getFrequencyInMinutes(), equalTo(TEN_MINUTES));

        mocksControl.verify();
    }

    @Test
    public void shouldReturnSixHundredThousandMilliSecondsAsTheDelay() {
        primeConfigWithInvalidDelay();
        mocksControl.replay();

        assertThat(new TimerDelay(mockConfig).getFrequencyInMilliSeconds(), equalTo(TEN_MINUTES_IN_MS));

        mocksControl.verify();
    }

    private void primeConfigWithInvalidDelay() {
        EasyMock.expect(mockConfig.getRepeatFrequencyInMinutes()).andReturn("not_a_number");
    }
}
