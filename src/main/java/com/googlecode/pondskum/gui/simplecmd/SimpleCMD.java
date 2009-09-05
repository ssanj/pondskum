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

import com.googlecode.pinthura.util.SystemPropertyRetrieverImpl;
import com.googlecode.pondskum.client.BigpondConnectorImpl;
import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.config.Config;
import com.googlecode.pondskum.config.DefaultConfigLoader;

//TODO:Rename to SimpleCMDRunner.
public final class SimpleCMD {

    private SimpleCMD() {
        //runner.
    }

    private static final int SECONDS_IN_MILLISECONDS = 1000;

    public static void main(final String[] args) {
        try {
            double startTime = System.currentTimeMillis();
            BigpondUsageInformation usageInformation = new BigpondConnectorImpl(loadUserConfiguration()).connect();

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
        String lineSeparator = new SystemPropertyRetrieverImpl().getLineSeparator();
        System.out.println(lineSeparator);
        System.out.println(e.getMessage());
        System.out.println(lineSeparator);
        e.printStackTrace();
    }

    private static Config loadUserConfiguration() throws Exception {
        return new DefaultConfigLoader().loadConfig();
    }
}
