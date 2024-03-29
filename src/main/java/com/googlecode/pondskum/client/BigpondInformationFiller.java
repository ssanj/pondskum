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
package com.googlecode.pondskum.client;

import com.googlecode.pinthura.annotation.SuppressionReason;

import java.util.HashMap;
import java.util.Map;

public final class BigpondInformationFiller implements InformationFiller {

    private final Map<String, String> accountInfoMap;

    @SuppressWarnings({"InstanceVariableOfConcreteClass"})
    @SuppressionReason(SuppressionReason.Reason.BUILDER_PATTERN)
    private final BigpondUsageInformationBuilder informationBuilder;

    public BigpondInformationFiller() {
        accountInfoMap = new HashMap<String, String>();
        accountInfoMap.put("Account Name:", "N/A");
        accountInfoMap.put("Billing Account Number:", "N/A");
        accountInfoMap.put("Current Plan:", "N/A");
        accountInfoMap.put("Monthly Plan Allowance:", "N/A");
        accountInfoMap.put("Monthly Plan Fee:", "N/A");

        informationBuilder = new BigpondUsageInformationBuilder();
    }

    public void fillAccountInfo(final String[] data, final int index) {
        if (accountInfoMap.keySet().contains(data[index])) {
            accountInfoMap.put(data[index], data[index + 1]);
        }
    }

    public void fillUsageInfo(final String[] data, final int index) {
        int copyOfIndex = index;

        informationBuilder.withAccountInformation().
                            havingAccountName(accountInfoMap.get("Account Name:")).
                            withAccountNumber(accountInfoMap.get("Billing Account Number:")).
                            onCurrentPlan(accountInfoMap.get("Current Plan:")).
                withAMonthlyAllowanceShapingOf(accountInfoMap.get("Monthly Plan Allowance:")).
                            forAPlanFeeOf(accountInfoMap.get("Monthly Plan Fee:")).done();

        while (!data[++copyOfIndex].equals("Total")) {
            informationBuilder.addMonthlyUsage().
                                forMonth(data[copyOfIndex]).
                                havingDownloadUsageOf(data[++copyOfIndex]).
                                havingUploadUsageOf(data[++copyOfIndex]).
                                withTotalUsageOf(data[++copyOfIndex]).
                                havingUmeteredUsageOf(data[++copyOfIndex]).
                                done();
        }

        informationBuilder.withTotalUsage().
                            havingDownloadUsageOf(data[++copyOfIndex]).
                            havingUploadUsageOf(data[++copyOfIndex]).
                            withTotalUsageOf(data[++copyOfIndex]).
                            havingUmeteredUsageOf(data[++copyOfIndex]).done();
    }

    public BigpondUsageInformation getFilledInformation() {
        return informationBuilder.build();
    }
}
