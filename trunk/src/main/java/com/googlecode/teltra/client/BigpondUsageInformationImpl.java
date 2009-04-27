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
package com.googlecode.teltra.client;

import java.util.ArrayList;
import java.util.List;

public final class BigpondUsageInformationImpl implements BigpondUsageInformation {

    private BigpondAccountInformation accountInformation;
    private List<BigpondMonthlyUsage> monthlyUsageList;
    private BigpondUsage totalUsage;

    public BigpondUsageInformationImpl(final BigpondAccountInformation accountInformation, final List<BigpondMonthlyUsage> monthlyUsageList, final BigpondUsage totalUsage) {
        this.accountInformation = accountInformation;
        this.monthlyUsageList = monthlyUsageList;
        this.totalUsage = totalUsage;
    }

    public BigpondAccountInformation getAccountInformation() {
        return accountInformation;
    }

    public List<BigpondMonthlyUsage> getMonthlyUsageList() {
        return new ArrayList<BigpondMonthlyUsage>(monthlyUsageList);
    }

    public BigpondUsage getTotalUsage() {
        return totalUsage;
    }

    @Override
    public String toString() {
        return "BigpondUsageInformationImpl{" +
                "accountInformation=" + accountInformation +
                ", monthlyUsageList=" + monthlyUsageList +
                ", totalUsage=" + totalUsage +
                '}';
    }
}
