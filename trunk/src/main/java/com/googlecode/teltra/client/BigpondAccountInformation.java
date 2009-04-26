package com.googlecode.teltra.client;

public final class BigpondAccountInformation {

    private String accountName;
    private String accountNumber;
    private String currentPlan;
    private String monthlyAllowance;
    private String monthlyPlanFee;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(final String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(final String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(final String currentPlan) {
        this.currentPlan = currentPlan;
    }

    public String getMonthlyAllowance() {
        return monthlyAllowance;
    }

    public void setMonthlyAllowance(final String monthlyAllowance) {
        this.monthlyAllowance = monthlyAllowance;
    }

    public String getMonthlyPlanFee() {
        return monthlyPlanFee;
    }

    public void setMonthlyPlanFee(final String monthlyPlanFee) {
        this.monthlyPlanFee = monthlyPlanFee;
    }

    @Override
    public String toString() {
        return "BigpondAccountInformation{" +
                "accountName='" + accountName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", currentPlan='" + currentPlan + '\'' +
                ", monthlyAllowance='" + monthlyAllowance + '\'' +
                ", monthlyPlanFee='" + monthlyPlanFee + '\'' +
                '}';
    }
}
