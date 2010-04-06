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
package com.googlecode.pondskum.gui.simplecmd;

import com.googlecode.pondskum.bootstrap.ClientBuilder;
import com.googlecode.pondskum.client.BigpondConnectorImpl;
import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.config.Config;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SimpleCMDRunner {

    private static final int SECONDS_IN_MILLISECONDS = 1000;

    private SimpleCMDRunner() {
        //runner.
    }

    public static void main(final String[] args) throws Exception {
        ClientBuilder builder = new ClientBuilder();
        retrieveUsage(builder.getConfig(), builder.getLogger(SimpleCMDRunner.class));
    }

    private static void retrieveUsage(final Config config, final Logger logger) {
        double startTime = getStartTime();
        try {
            BigpondUsageInformation usageInformation = connect(config);
            printUsage(logger, startTime, usageInformation);
        } catch (Exception e) {
            dumpException(logger, e);
        }
    }

    private static long getStartTime() {
        return System.currentTimeMillis();
    }

    private static void printUsage(final Logger logger, final double startTime, final BigpondUsageInformation usageInformation) {
        System.out.println(new UsageMessageBuilder().
                withUsageInformation(usageInformation).
                displayAccountName().
                displayTotalUsage().
                displayDownloadUsage().
                displayUploadUsage().
                build());

        logger.info("Time taken: " + (getEndTimeInMilliseconds(startTime) / SECONDS_IN_MILLISECONDS) + " seconds");
        logger.info("");
    }

    private static double getEndTimeInMilliseconds(final double startTime) {
        return System.currentTimeMillis() - startTime;
    }

    private static BigpondUsageInformation connect(final Config config) {
        System.out.println("Connecting...");
        BigpondUsageInformation usageInformation = new BigpondConnectorImpl(config).connect();
        System.out.println("");
        return usageInformation;
    }

    private static void dumpException(final Logger logger, final Exception e) {
        logger.addHandler(new ConsoleHandler());
        logger.severe("There seems to have been a problem. See below for details.");
        logger.severe("");
        logger.severe(e.getMessage());
        logger.severe("");
        logger.log(Level.SEVERE, "Detailed error report", e);
    }
}
