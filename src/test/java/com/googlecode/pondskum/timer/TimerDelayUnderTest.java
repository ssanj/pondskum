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

public final class TimerDelayUnderTest {

    private Config mockConfig;
    private MocksControl mocksControl;

    @Before
    public void setup() {
        mocksControl = new MocksControl(MocksControl.MockType.DEFAULT);
        mockConfig = mocksControl.createMock(Config.class);
    }

    @Test
    public void shouldReturnItsFrequencyInMinutesForADelay() {
        expectDelayInMinutes("30");
    }

    @Test
    public void shouldReturnItsFrequencyInMinutesForAnotherDelay() {
        expectDelayInMinutes("45");
    }

    @Test
    public void shouldReturnTenMinutesIfTheDelaySuppliedIsLessThanTenMinutes() {
        primeConfigWithDelayInMinutes("9");
        mocksControl.replay();

        RepeatFrequency timerDelay = new TimerDelay(mockConfig);
        assertThat(timerDelay.getFrequencyInMinutes(), equalTo(Integer.parseInt("10")));

        mocksControl.verify();
    }

    @Test
    public void shouldReturnItsFrequencyInMilliSecondsForADelay() {
        primeConfigWithDelayInMinutes("45");
        mocksControl.replay();

        RepeatFrequency timerDelay = new TimerDelay(mockConfig);
        assertThat(timerDelay.getFrequencyInMilliSeconds(), equalTo(2700000));

        mocksControl.verify();
    }

    @Test
    public void shouldReturnItsFrequencyInMiliiSecondsForAnotherDelay() {
        primeConfigWithDelayInMinutes("30");
        mocksControl.replay();

        RepeatFrequency timerDelay = new TimerDelay(mockConfig);
        assertThat(timerDelay.getFrequencyInMilliSeconds(), equalTo(1800000));

        mocksControl.verify();
    }

    private void expectDelayInMinutes(final String delayInMinutes) {
        primeConfigWithDelayInMinutes(delayInMinutes);
        mocksControl.replay();

        RepeatFrequency timerDelay = new TimerDelay(mockConfig);
        assertThat(timerDelay.getFrequencyInMinutes(), equalTo(Integer.parseInt(delayInMinutes)));

        mocksControl.verify();
    }

    private void primeConfigWithDelayInMinutes(final String delayInMinutes) {
        EasyMock.expect(mockConfig.getRepeatFrequencyInMinutes()).andReturn(delayInMinutes);
    }
}
