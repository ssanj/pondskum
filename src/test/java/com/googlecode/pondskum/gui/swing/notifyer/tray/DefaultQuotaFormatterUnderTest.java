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
package com.googlecode.pondskum.gui.swing.notifyer.tray;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public final class DefaultQuotaFormatterUnderTest {

    private QuotaFormatter formatter;

    @Before public void setup() {
        formatter = new DefaultQuotaFormatter();
    }

    @Test public void shouldFormatValuesThatAreInvalid() {
        assertThat(formatter.formatQuota("avnvnds"), equalTo("0.00 GB"));            
    }

    @Test public void shouldFormatValuesThatAreZeroGBInSize() {
        assertThat(formatter.formatQuota("0"), equalTo("0.00 GB"));
        assertThat(formatter.formatQuota("1"), equalTo("0.00 GB"));
        assertThat(formatter.formatQuota("2"), equalTo("0.00 GB"));
        assertThat(formatter.formatQuota("3"), equalTo("0.00 GB"));
        assertThat(formatter.formatQuota("4"), equalTo("0.00 GB"));
    }

    @Test public void shouldFormatValuesThatAreOneHundrethOfAGBInSize() {
        assertThat(formatter.formatQuota("5"), equalTo("0.01 GB"));
        assertThat(formatter.formatQuota("10"), equalTo("0.01 GB"));
        assertThat(formatter.formatQuota("32"), equalTo("0.03 GB"));
        assertThat(formatter.formatQuota("56"), equalTo("0.06 GB"));
        assertThat(formatter.formatQuota("89"), equalTo("0.09 GB"));
    }

    @Test public void shouldFormatValuesThatAreOneTenthOfAGBInSize() {
        assertThat(formatter.formatQuota("100"), equalTo("0.10 GB"));
        assertThat(formatter.formatQuota("256"), equalTo("0.26 GB"));
        assertThat(formatter.formatQuota("876"), equalTo("0.88 GB"));
    }

    @Test public void shouldFormatValuesThatAreInGBSize() {
        assertThat(formatter.formatQuota("999"), equalTo("1.00 GB"));
        assertThat(formatter.formatQuota("5000"), equalTo("5.00 GB"));
        assertThat(formatter.formatQuota("6789"), equalTo("6.79 GB"));
        assertThat(formatter.formatQuota("8123"), equalTo("8.12 GB"));
    }

    @Test public void shouldFormatValuesThatAreInTensOfGBSize() {
        assertThat(formatter.formatQuota("10001"), equalTo("10.00 GB"));
        assertThat(formatter.formatQuota("15345"), equalTo("15.35 GB"));
        assertThat(formatter.formatQuota("19998"), equalTo("20.00 GB"));
        assertThat(formatter.formatQuota("98567"), equalTo("98.57 GB"));
    }

    @Test public void shouldFormatValuesThatAreInHundredsOfGBSize() {
        assertThat(formatter.formatQuota("101456"), equalTo("101.46 GB"));
        assertThat(formatter.formatQuota("512875"), equalTo("512.88 GB"));
        assertThat(formatter.formatQuota("999897"), equalTo("999.90 GB"));
    }
}
