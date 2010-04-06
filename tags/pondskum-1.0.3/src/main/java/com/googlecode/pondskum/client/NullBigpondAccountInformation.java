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

public final class NullBigpondAccountInformation implements BigpondAccountInformation {
    
    @Override
    public String getAccountName() {
        return "";
    }

    @Override
    public void setAccountName(final String accountName) {
        //do nothing.
    }

    @Override
    public String getAccountNumber() {
        return "";
    }

    @Override
    public void setAccountNumber(final String accountNumber) {
        //do nothing.
    }

    @Override
    public String getCurrentPlan() {
        return "";
    }

    @Override
    public void setCurrentPlan(final String currentPlan) {
        //do nothing.
    }

    @Override
    public String getMonthlyAllowanceShaping() {
        return "";
    }

    @Override
    public int getMonthlyAllowance() {
        return 0;
    }

    @Override
    public void setMonthlyAllowance(final int monthlyAllowance) {
        //do nothing.
    }

    @Override
    public void setMonthlyAllowanceShaping(final String monthlyAllowance) {
        //do nothing.
    }

    @Override
    public String getMonthlyPlanFee() {
        return "";
    }

    @Override
    public void setMonthlyPlanFee(final String monthlyPlanFee) {
        //do nothing.
    }
}
