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

import com.googlecode.pondskum.client.BigpondConnectorImpl;
import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.config.ConfigFileLoaderImpl;
import com.googlecode.pondskum.config.SystemPropertyRetrieverImpl;

import java.util.Properties;

public final class SimpleCMD {

    private static final int SECONDS_IN_MILLISECONDS = 1000;

    public static void main(String[] args) {
        try {
            double startTime = System.currentTimeMillis();
            BigpondUsageInformation usageInformation = new BigpondConnectorImpl(loadProperties()).connect();

            System.out.println(new SimpleCMDBuilder().
                    withUsageInformation(usageInformation).
                    displayAccountName().
                    displayTotalUsage().
                    displayDownloadUsage().
                    displayUploadUsage().
                    build());

            System.out.print("Time taken: " + ((System.currentTimeMillis() - startTime) / SECONDS_IN_MILLISECONDS) + " seconds");
            System.out.println();
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
