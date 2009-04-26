package com.googlecode.teltra.client;

import java.util.Map;
import java.util.HashMap;

public final class BigpondInformationFiller implements InformationFiller {

    private final Map<String, String> accountInfoMap;
    private final BigpondUsageInfomationBuilder infomationBuilder;

    public BigpondInformationFiller() {
        accountInfoMap = new HashMap<String, String>();
        accountInfoMap.put("Account Name:", "N/A");
        accountInfoMap.put("BigPond Account Number:", "N/A");
        accountInfoMap.put("Current Plan:", "N/A");
        accountInfoMap.put("Monthly Plan Allowance:", "N/A");
        accountInfoMap.put("Monthly Plan Fee:", "N/A");

        infomationBuilder = new BigpondUsageInfomationBuilder();
    }

    public void fillAccountInfo(final String[] data, final int index) {
        if (accountInfoMap.keySet().contains(data[index])) {
            accountInfoMap.put(data[index], data[index + 1]);
        }
    }

    public void fillUsageInfo(final String[] data, final int index) {
        int copyOfIndex = index;

        infomationBuilder.withAccountInformation().
                            havingAccountName(accountInfoMap.get("Account Name:")).
                            withAccountNumber(accountInfoMap.get("BigPond Account Number:")).
                            onCurrentPlan(accountInfoMap.get("Current Plan:")).
                            withAMonthlyAllowanceOf(accountInfoMap.get("Monthly Plan Allowance:")).
                            forAPlanFeeOf(accountInfoMap.get("Monthly Plan Fee:")).done();

        while (!data[++copyOfIndex].equals("Total")) {
            infomationBuilder.addMonthlyUsage().
                                forMonth(data[copyOfIndex]).
                                havingDownloadUsageOf(data[++copyOfIndex]).
                                havingUploadUsageOf(data[++copyOfIndex]).
                                withTotalUsageOf(data[++copyOfIndex]).
                                havingUmeteredUsageOf(data[++copyOfIndex]).
                                done();
        }

        infomationBuilder.withTotalUsage().
                            havingDownloadUsageOf(data[++copyOfIndex]).
                            havingUploadUsageOf(data[++copyOfIndex]).
                            withTotalUsageOf(data[++copyOfIndex]).
                            havingUmeteredUsageOf(data[++copyOfIndex]).done();
    }

    public BigpondUsageInformation getFilledInformation() {
        return infomationBuilder.build();
    }
}
