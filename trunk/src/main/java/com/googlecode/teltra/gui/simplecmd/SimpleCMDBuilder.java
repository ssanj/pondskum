package com.googlecode.teltra.gui.simplecmd;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.teltra.client.BigpondUsageInformation;
import com.googlecode.teltra.config.SystemPropertyRetrieverImpl;

@SuppressWarnings({"MethodReturnOfConcreteClass"})
@SuppressionReason(SuppressionReason.Reason.BUILDER_PATTERN)
public final class SimpleCMDBuilder {

    private final String lineSeparator;
    private final StringBuilder stringBuilder;
    private BigpondUsageInformation usageInformation;

    public SimpleCMDBuilder() {
        lineSeparator = new SystemPropertyRetrieverImpl().retrieveProperty("line.separator");
        stringBuilder = new StringBuilder();

        stringBuilder.append("connecting...");
        stringBuilder.append(lineSeparator);
    }


    public SimpleCMDBuilder withUsageInformation(final BigpondUsageInformation usageInformation) {
        this.usageInformation = usageInformation;
        return this;
    }

    public SimpleCMDBuilder displayAccountName() {
        stringBuilder.append("Account:    ");
        stringBuilder.append(usageInformation.getAccountInformation().getAccountName());
        stringBuilder.append(lineSeparator);
        return this;
    }

    public SimpleCMDBuilder displayTotalUsage() {
        stringBuilder.append("Total:      ");
        stringBuilder.append(getUsage(usageInformation.getTotalUsage().getTotalUsage()));
        stringBuilder.append(lineSeparator);
        return this;
    }

    public SimpleCMDBuilder displayDownloadUsage() {
        stringBuilder.append("Downloads:  ");
        stringBuilder.append(getUsage(usageInformation.getTotalUsage().getDownloadUsage()));
        stringBuilder.append(lineSeparator);
        return this;
    }

    public SimpleCMDBuilder displayUploadUsage() {
        stringBuilder.append("Uploads:    ");
        stringBuilder.append(getUsage(usageInformation.getTotalUsage().getUploadUsage()));
        stringBuilder.append(lineSeparator);
        return this;
    }

    public String build() {
        return stringBuilder.toString();
    }

    private static String getUsage(final String usage) {
        Double numericUsage = Double.parseDouble(usage);
        return  (numericUsage / 1000) + " GB ";
    }
}
