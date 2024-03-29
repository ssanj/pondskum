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
import com.googlecode.pondskum.util.NumericUtil;
import com.googlecode.pondskum.util.NumericUtilImpl;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"InstanceVariableOfConcreteClass", "MethodReturnOfConcreteClass", "MethodParameterOfConcreteClass"})
@SuppressionReason(SuppressionReason.Reason.BUILDER_PATTERN)
public final class BigpondUsageInformationBuilder {

    private final List<BigpondMonthlyUsageBuilder> monthlyUsageBuilderList;
    private BigpondUsageBuilderImpl totalUsageBuilder;
    private BigpondAccountInformationBuilder informationBuilder;

    public BigpondUsageInformationBuilder() {
        monthlyUsageBuilderList = new ArrayList<BigpondMonthlyUsageBuilder>();
    }

    public BigpondAccountInformationBuilder withAccountInformation() {
        informationBuilder = new BigpondAccountInformationBuilder(this);
        return informationBuilder;
    }

    public BigpondMonthlyUsageBuilder addMonthlyUsage() {
        final BigpondMonthlyUsageBuilder monthlyUsageBuilder = new BigpondMonthlyUsageBuilder(new BigpondUsageBuilderImpl(this));
        monthlyUsageBuilderList.add(monthlyUsageBuilder);
        return monthlyUsageBuilder;
    }

    public BigpondUsageBuilder withTotalUsage() {
        totalUsageBuilder = new BigpondUsageBuilderImpl(this);
        return totalUsageBuilder;
    }

    public BigpondUsageInformation build() {
        final List<BigpondMonthlyUsage> monthlyUsageList = new ArrayList<BigpondMonthlyUsage>();

        for (BigpondMonthlyUsageBuilder bigpondUsageBuilder : monthlyUsageBuilderList) {
            monthlyUsageList.add(bigpondUsageBuilder.build());
        }

        return new BigpondUsageInformationImpl(informationBuilder.build(), monthlyUsageList, totalUsageBuilder.build());
    }

    public static final class BigpondMonthlyUsageBuilder implements BigpondUsageBuilder {

        private final BigpondUsageBuilderImpl telstraUsageBuilder;
        private String month;

        @SuppressWarnings({"MethodParameterOfConcreteClass"})
        @SuppressionReason(SuppressionReason.Reason.BUILDER_PATTERN)
        public BigpondMonthlyUsageBuilder(final BigpondUsageBuilderImpl telstraUsageBuilder) {
            this.telstraUsageBuilder = telstraUsageBuilder;
        }

        public BigpondMonthlyUsageBuilder forMonth(final String month) {
            this.month = month;
            return this;
        }

        public BigpondUsageBuilder havingDownloadUsageOf(final String downloadUsage) {
            return telstraUsageBuilder.havingDownloadUsageOf(downloadUsage);
        }

        public BigpondUsageBuilder havingUploadUsageOf(final String uploadUsage) {
            return telstraUsageBuilder.havingUploadUsageOf(uploadUsage);
        }

        public BigpondUsageBuilder withTotalUsageOf(final String totalUsage) {
            return telstraUsageBuilder.withTotalUsageOf(totalUsage);
        }

        public BigpondUsageBuilder havingUmeteredUsageOf(final String unmeteredUsage) {
            return telstraUsageBuilder.havingUmeteredUsageOf(unmeteredUsage);
        }

        public BigpondUsageInformationBuilder done() {
            return telstraUsageBuilder.done();
        }

        private BigpondMonthlyUsage build() {
            return new BigpondMonthlyUsageImpl(month, telstraUsageBuilder.build());
        }
    }

    public interface BigpondUsageBuilder {

        BigpondUsageBuilder havingDownloadUsageOf(String downloadUsage);

        BigpondUsageBuilder havingUploadUsageOf(String uploadUsage);

        BigpondUsageBuilder withTotalUsageOf(String totalUsage);

        BigpondUsageBuilder havingUmeteredUsageOf(String unmeteredUsage);

        BigpondUsageInformationBuilder done();
    }

    public static final class BigpondUsageBuilderImpl implements BigpondUsageBuilder {

        private final BigpondUsageInformationBuilder parent;
        private String downloadUsage;
        private String uploadUsage;
        private String totalUsage;
        private String unmeteredUsage;

        @SuppressWarnings({"MethodParameterOfConcreteClass"})
        @SuppressionReason(SuppressionReason.Reason.BUILDER_PATTERN)
        public BigpondUsageBuilderImpl(final BigpondUsageInformationBuilder parent) {
            this.parent = parent;
        }

        public BigpondUsageBuilderImpl havingDownloadUsageOf(final String downloadUsage) {
            this.downloadUsage = downloadUsage;
            return this;
        }

        public BigpondUsageBuilderImpl havingUploadUsageOf(final String uploadUsage) {
            this.uploadUsage = uploadUsage;
            return this;
        }

        public BigpondUsageBuilderImpl withTotalUsageOf(final String totalUsage) {
            this.totalUsage = totalUsage;
            return this;
        }

        public BigpondUsageBuilderImpl havingUmeteredUsageOf(final String unmeteredUsage) {
            this.unmeteredUsage = unmeteredUsage;
            return this;
        }

        public BigpondUsageInformationBuilder done() {
            return parent;
        }

        private BigpondUsage build() {
            final BigpondUsageImpl usage = new BigpondUsageImpl();
            usage.setDownloadUsage(downloadUsage);
            usage.setUploadUsage(uploadUsage);
            usage.setUnmeteredUsage(unmeteredUsage);
            usage.setTotalUsage(totalUsage);
            return usage;
        }
    }

    public static final class BigpondAccountInformationBuilder {

        private final BigpondUsageInformationBuilder parent;
        private String accountName;
        private String accountNumber;
        private String currentPlan;
        private String monthlyAllowanceShaping;
        private String monthlyPlanFee;
        private Integer monthlyAllowance = 0;
        private NumericUtil numericUtil;

        @SuppressWarnings({"MethodParameterOfConcreteClass"})
        @SuppressionReason(SuppressionReason.Reason.BUILDER_PATTERN)
        public BigpondAccountInformationBuilder(final BigpondUsageInformationBuilder parent) {
            this.parent = parent;
            numericUtil = new NumericUtilImpl();
        }

        public BigpondAccountInformationBuilder havingAccountName(final String accountName) {
            this.accountName = accountName;
            return this;
        }

        public BigpondAccountInformationBuilder withAccountNumber(final String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public BigpondAccountInformationBuilder onCurrentPlan(final String currentPlan) {
            this.currentPlan = currentPlan;
            return this;
        }

        public BigpondAccountInformationBuilder withAMonthlyAllowanceShapingOf(final String monthlyAllowanceShaping) {
            this.monthlyAllowanceShaping = monthlyAllowanceShaping;
            setMonthlyAllowance(monthlyAllowanceShaping);
            return this;
        }

        private void setMonthlyAllowance(final String monthlyAllowanceShaping) {
            int endIndex = monthlyAllowanceShaping.indexOf("G"); //find the first GB
            String numericAllowance = monthlyAllowanceShaping.substring(0, Math.max(0, endIndex));
            monthlyAllowance = numericUtil.getNumber(numericAllowance);
        }

        public BigpondAccountInformationBuilder forAPlanFeeOf(final String monthlyPlanFee) {
            this.monthlyPlanFee = monthlyPlanFee;
            return this;
        }

        public BigpondUsageInformationBuilder done() {
            return parent;
        }

        public BigpondAccountInformation build() {
            final BigpondAccountInformation accountInformation = new BigpondAccountInformationImpl();
            accountInformation.setAccountName(accountName);
            accountInformation.setAccountNumber(accountNumber);
            accountInformation.setCurrentPlan(currentPlan);
            accountInformation.setMonthlyAllowanceShaping(monthlyAllowanceShaping);
            accountInformation.setMonthlyAllowance(monthlyAllowance);
            accountInformation.setMonthlyPlanFee(monthlyPlanFee);
            return accountInformation;
        }
    }
}
