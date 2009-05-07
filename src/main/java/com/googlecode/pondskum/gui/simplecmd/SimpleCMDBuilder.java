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
package com.googlecode.pondskum.gui.simplecmd;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.config.SystemPropertyRetrieverImpl;
import com.googlecode.pondskum.util.NumericUtil;
import com.googlecode.pondskum.util.NumericUtilImpl;

@SuppressWarnings({"MethodReturnOfConcreteClass"})
@SuppressionReason(SuppressionReason.Reason.BUILDER_PATTERN)
public final class SimpleCMDBuilder {

    private final String lineSeparator;
    private final StringBuilder stringBuilder;
    private final NumericUtil numericUtil;
    private BigpondUsageInformation usageInformation;

    public SimpleCMDBuilder() {
        lineSeparator = new SystemPropertyRetrieverImpl().retrieveProperty("line.separator");
        stringBuilder = new StringBuilder();
        numericUtil = new NumericUtilImpl();

        stringBuilder.append("connecting...");
        stringBuilder.append(lineSeparator);
    }


    public SimpleCMDBuilder withUsageInformation(final BigpondUsageInformation usageInformation) {
        this.usageInformation = usageInformation;
        return this;
    }

    public SimpleCMDBuilder displayAccountName() {
        stringBuilder.append("Account:    ");
        stringBuilder.append(usageInformation.getAccountInformation().getAccountName());
        stringBuilder.append(lineSeparator);
        return this;
    }

    public SimpleCMDBuilder displayTotalUsage() {
        stringBuilder.append("Total:      ");
        stringBuilder.append(getUsage(usageInformation.getTotalUsage().getTotalUsage()));
        stringBuilder.append(lineSeparator);
        return this;
    }

    public SimpleCMDBuilder displayDownloadUsage() {
        stringBuilder.append("Downloads:  ");
        stringBuilder.append(getUsage(usageInformation.getTotalUsage().getDownloadUsage()));
        stringBuilder.append(lineSeparator);
        return this;
    }

    public SimpleCMDBuilder displayUploadUsage() {
        stringBuilder.append("Uploads:    ");
        stringBuilder.append(getUsage(usageInformation.getTotalUsage().getUploadUsage()));
        stringBuilder.append(lineSeparator);
        return this;
    }

    public String build() {
        return stringBuilder.toString();
    }

    private String getUsage(final String usage) {
        int numericUsage = numericUtil.getNumber(usage);
        return  (numericUsage / 1000) + " GB ";
    }
}
