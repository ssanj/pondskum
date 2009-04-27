package com.googlecode.teltra.client;

public final class BigpondMonthlyUsageImpl implements BigpondMonthlyUsage {

    private final String month;
    private final BigpondUsage bigpondUsage;

    public BigpondMonthlyUsageImpl(final String month, final BigpondUsage bigpondUsage) {
        this.bigpondUsage = bigpondUsage;
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public BigpondUsage getBigpondUsage() {
        return bigpondUsage;
    }

    @Override
    public String toString() {
        return "BigpondMonthlyUsageImpl{" +
                "month='" + month + '\'' +
                ", bigpondUsage=" + bigpondUsage +
                '}';
    }
}
