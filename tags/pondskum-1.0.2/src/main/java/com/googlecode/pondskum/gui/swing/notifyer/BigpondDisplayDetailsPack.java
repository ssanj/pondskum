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
package com.googlecode.pondskum.gui.swing.notifyer;

import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.util.NumericUtil;
import com.googlecode.pondskum.util.NumericUtilImpl;

public final class BigpondDisplayDetailsPack extends AbstractDisplayDetailsPack {

    private static final double GIGABYTE = 1000.0d;

    private final BigpondUsageInformation usageInformation;
    private NumericUtil numericUtil;

    public BigpondDisplayDetailsPack(final BigpondUsageInformation usageInformation) {
        this.usageInformation = usageInformation;
        numericUtil = new NumericUtilImpl();
    }

    @Override
    public String getQuotaUnits() {
        return "GB";
    }

    @Override
    public Integer getQuotaLimit() {
        return usageInformation.getAccountInformation().getMonthlyAllowance();
    }

    @Override
    public Double getTotalUsage() {
        return numericUtil.getNumber(usageInformation.getTotalUsage().getTotalUsage()) / GIGABYTE;
    }

    @Override
    public String getAccountName() {
        return usageInformation.getAccountInformation().getAccountName();
    }

    @Override
    public String getPlanName() {
        return usageInformation.getAccountInformation().getCurrentPlan();
    }
}
