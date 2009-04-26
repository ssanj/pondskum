package com.googlecode.teltra.client;

import java.util.ArrayList;
import java.util.List;

public final class BigpondUsageInfomationBuilder {

    private final List<BigpondMonthlyUsageBuilder> monthlyUsageBuilderList;
    private BigpondUsageBuilderImpl totalUsageBuilder;
    private BigpondAccountInformationBuilder informationBuilder;

    public BigpondUsageInfomationBuilder() {
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

    public BigpondUsageBuilderImpl withTotalUsage() {
        totalUsageBuilder = new BigpondUsageBuilderImpl(this);
        return totalUsageBuilder;
    }

    public BigpondUsageInformation build() {
        final List<BigpondMonthlyUsage> monthlyUsageList = new ArrayList<BigpondMonthlyUsage>();

        for (BigpondMonthlyUsageBuilder bigpondUsageBuilder : monthlyUsageBuilderList) {
            monthlyUsageList.add(bigpondUsageBuilder.build());
        }

        return new BigpondUsageInformation(informationBuilder.build(), monthlyUsageList, totalUsageBuilder.build());
    }

    public static final class BigpondMonthlyUsageBuilder implements BigpondUsageBuilder {

        private final BigpondUsageBuilderImpl telstraUsageBuilder;
        private String month;

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

        public BigpondUsageInfomationBuilder done() {
            return telstraUsageBuilder.done();
        }

        private BigpondMonthlyUsage build() {
            return new BigpondMonthlyUsage(month, telstraUsageBuilder.build());
        }
    }

    public interface BigpondUsageBuilder {

        BigpondUsageBuilder havingDownloadUsageOf(String downloadUsage);

        BigpondUsageBuilder havingUploadUsageOf(String uploadUsage);

        BigpondUsageBuilder withTotalUsageOf(String totalUsage);

        BigpondUsageBuilder havingUmeteredUsageOf(String unmeteredUsage);

        BigpondUsageInfomationBuilder done();
    }

    public static final class BigpondUsageBuilderImpl implements BigpondUsageBuilder {

        private final BigpondUsageInfomationBuilder parent;
        private String downloadUsage;
        private String uploadUsage;
        private String totalUsage;
        private String unmeteredUsage;

        public BigpondUsageBuilderImpl(final BigpondUsageInfomationBuilder parent) {
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

        public BigpondUsageInfomationBuilder done() {
            return parent;
        }

        private BigpondUsage build() {
            final BigpondUsage usage = new BigpondUsage();
            usage.setDownloadUsage(downloadUsage);
            usage.setUploadUsage(uploadUsage);
            usage.setUnmeteredUsage(unmeteredUsage);
            usage.setTotalUsage(totalUsage);
            return usage;
        }
    }

    public static final class BigpondAccountInformationBuilder {

        private final BigpondUsageInfomationBuilder parent;
        private String accountName;
        private String accountNumber;
        private String currentPlan;
        private String monthlyAllowance;
        private String monthlyPlanFee;

        public BigpondAccountInformationBuilder(final BigpondUsageInfomationBuilder parent) {
            this.parent = parent;
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

        public BigpondAccountInformationBuilder withAMonthlyAllowanceOf(final String monthlyAllowance) {
            this.monthlyAllowance = monthlyAllowance;
            return this;
        }

        public BigpondAccountInformationBuilder forAPlanFeeOf(final String monthlyPlanFee) {
            this.monthlyPlanFee = monthlyPlanFee;
            return this;
        }

        public BigpondUsageInfomationBuilder done() {
            return parent;
        }

        private BigpondAccountInformation build() {
            final BigpondAccountInformation accountInformation = new BigpondAccountInformation();
            accountInformation.setAccountName(accountName);
            accountInformation.setAccountNumber(accountNumber);
            accountInformation.setCurrentPlan(currentPlan);
            accountInformation.setMonthlyAllowance(monthlyAllowance);
            accountInformation.setMonthlyPlanFee(monthlyPlanFee);
            return accountInformation;
        }
    }
}
