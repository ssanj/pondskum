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

import com.googlecode.pondskum.client.BigpondAccountInformation;
import com.googlecode.pondskum.client.BigpondUsage;
import com.googlecode.pondskum.client.BigpondUsageInformation;
import org.easymock.EasyMock;
import org.easymock.internal.MocksControl;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public final class UsageFormatterUnderTest {

    private UsageFormatter usageFormatter;
    private MocksControl mocksControl;
    private QuotaFormatter mockQuotaFormatter;

    @Before
    public void setup() {
        mocksControl = new MocksControl(MocksControl.MockType.DEFAULT);
        mockQuotaFormatter = mocksControl.createMock(QuotaFormatter.class);
        usageFormatter = new UsageFormatter(mockQuotaFormatter);
    }

    @Test public void shouldReturnAFormattedStringForValidData() {
        BigpondUsageInformation mockBigpondUsageInformation = mocksControl.createMock(BigpondUsageInformation.class);
        BigpondAccountInformation mockBigpondAccountInformation = mocksControl.createMock(BigpondAccountInformation.class);
        BigpondUsage mockTotalUsage = mocksControl.createMock(BigpondUsage.class);

        expectAccountInfo(mockBigpondUsageInformation, mockBigpondAccountInformation);
        expectUsageInfo(mockBigpondUsageInformation, mockTotalUsage);
        mocksControl.replay();

        final String formattedUsage = usageFormatter.formatUsage(mockBigpondUsageInformation);
        assertThat(formattedUsage, equalTo("Mr. Josh Pyke - ([U]1.24 GB | [D]6.58 GB | [T]7.89 GB)"));

        mocksControl.verify();
    }

    @Test public void shouldReturnAFormattedStringForInvalidData() {
        final String formattedUsage = usageFormatter.formatUsage(null);
        assertThat(formattedUsage, equalTo("(Bigpond usage information is unavailable at the moment.)"));
    }

    private void expectUsageInfo(final BigpondUsageInformation mockBigpondUsageInformation, final BigpondUsage mockTotalUsage) {
        EasyMock.expect(mockBigpondUsageInformation.getTotalUsage()).andReturn(mockTotalUsage);
        EasyMock.expect(mockTotalUsage.getDownloadUsage()).andReturn("6578");
        EasyMock.expect(mockTotalUsage.getUploadUsage()).andReturn("1236");
        EasyMock.expect(mockTotalUsage.getTotalUsage()).andReturn("7893");
        EasyMock.expect(mockQuotaFormatter.formatQuota("6578")).andReturn("6.58 GB");
        EasyMock.expect(mockQuotaFormatter.formatQuota("1236")).andReturn("1.24 GB");
        EasyMock.expect(mockQuotaFormatter.formatQuota("7893")).andReturn("7.89 GB");
    }

    private void expectAccountInfo(final BigpondUsageInformation mockBigpondUsageInformation,
                                   final BigpondAccountInformation mockBigpondAccountInformation) {
        EasyMock.expect(mockBigpondUsageInformation.getAccountInformation()).andReturn(mockBigpondAccountInformation);
        EasyMock.expect(mockBigpondAccountInformation.getAccountName()).andReturn("Mr. Josh Pyke");
    }
}
