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
package com.googlecode.pondscum.client;

public final class BigpondAccountInformationImpl implements BigpondAccountInformation {

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
        return "BigpondAccountInformationImpl{" +
                "accountName='" + accountName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", currentPlan='" + currentPlan + '\'' +
                ", monthlyAllowance='" + monthlyAllowance + '\'' +
                ", monthlyPlanFee='" + monthlyPlanFee + '\'' +
                '}';
    }
}
