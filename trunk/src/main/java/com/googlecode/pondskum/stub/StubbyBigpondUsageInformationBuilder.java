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
package com.googlecode.pondskum.stub;

import com.googlecode.pondskum.client.BigpondAccountInformation;
import com.googlecode.pondskum.client.BigpondAccountInformationImpl;
import com.googlecode.pondskum.client.BigpondMonthlyUsage;
import com.googlecode.pondskum.client.BigpondMonthlyUsageImpl;
import com.googlecode.pondskum.client.BigpondUsage;
import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.client.BigpondUsageInformationImpl;

import java.util.ArrayList;
import java.util.List;

public final class StubbyBigpondUsageInformationBuilder {

    private final StubInformationCreator informationCreator;

    public StubbyBigpondUsageInformationBuilder() {
        informationCreator = new StubInformationCreator();
    }

    public BigpondUsageInformation build() {
        BigpondAccountInformation accountInformation = new BigpondAccountInformationImpl();
        accountInformation.setAccountName("James Bond");
        accountInformation.setAccountNumber("0000007");
        accountInformation.setCurrentPlan("MI6");
        accountInformation.setMonthlyAllowance("Infinite");
        accountInformation.setMonthlyPlanFee("$1,000,000");

        List<BigpondMonthlyUsage> bigpondUsageList = new ArrayList<BigpondMonthlyUsage>();
        bigpondUsageList.add(new BigpondMonthlyUsageImpl("01 Jan 2009", informationCreator.createUsage("1000", "500", "250", "1750")));
        bigpondUsageList.add(new BigpondMonthlyUsageImpl("22 Feb 2009", informationCreator.createUsage("-", "-", "-", "-")));
        bigpondUsageList.add(new BigpondMonthlyUsageImpl("14 Mar 2009", informationCreator.createUsage("5000", "1000", "50", "6050")));

        BigpondUsage totalUsage = informationCreator.createUsage("6000", "1500", "300", "7800");

        return new BigpondUsageInformationImpl(accountInformation, bigpondUsageList, totalUsage);
    }
}
