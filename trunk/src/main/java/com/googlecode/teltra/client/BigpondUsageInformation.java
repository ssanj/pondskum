package com.googlecode.teltra.client;

import java.util.ArrayList;
import java.util.List;

public final class BigpondUsageInformation {

    private BigpondAccountInformation accountInformation;
    private List<BigpondMonthlyUsage> monthlyUsageList;
    private BigpondUsage totalUsage;

    public BigpondUsageInformation(final BigpondAccountInformation accountInformation, final List<BigpondMonthlyUsage> monthlyUsageList, final BigpondUsage totalUsage) {
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
        return "BigpondUsageInformation{" +
                "accountInformation=" + accountInformation +
                ", monthlyUsageList=" + monthlyUsageList +
                ", totalUsage=" + totalUsage +
                '}';
    }
}
