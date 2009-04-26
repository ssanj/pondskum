package com.googlecode.teltra.gui.simplecmd;

import com.googlecode.teltra.client.BigpondConnector;
import com.googlecode.teltra.client.BigpondUsageInformation;
import com.googlecode.teltra.config.ConfigFileLoaderImpl;
import com.googlecode.teltra.config.SystemPropertyRetrieverImpl;

import java.util.Properties;

public final class SimpleCMD {

    public static void main(String[] args) {
        try {
            double startTime = System.currentTimeMillis();
            BigpondUsageInformation usageInformation = new BigpondConnector(loadProperties()).connect();

            System.out.println(new SimpleCMDBuilder().
                    withUsageInformation(usageInformation).
                    displayAccountName().
                    displayTotalUsage().
                    displayDownloadUsage().
                    displayUploadUsage().
                    build());
            
            System.out.print("Time taken: " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds");
        } catch (Exception e) {
            dumpException(e);
        }
    }

    private static void dumpException(final Exception e) {
        System.out.println("There seems to have been a problem. See below for details.");
        String lineSeparator = new SystemPropertyRetrieverImpl().retrieveProperty("line.separator");
        System.out.println(lineSeparator);
        System.out.println(e.getMessage());
        System.out.println(lineSeparator);
        e.printStackTrace();
    }

    private static Properties loadProperties() throws Exception {
        return new ConfigFileLoaderImpl(new SystemPropertyRetrieverImpl()).loadProperties("bigpond.config.location");
    }
}
