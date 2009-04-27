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
package com.googlecode.teltra.gui.swing.tablet;

import com.googlecode.pondskum.client.BigpondConnectorImpl;
import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.teltra.config.ConfigFileLoaderImpl;
import com.googlecode.teltra.config.SystemPropertyRetrieverImpl;

import java.util.Properties;

public final class TabletRunner {

    public static void main(String[] args) {
        Properties properties = new ConfigFileLoaderImpl(new SystemPropertyRetrieverImpl()).loadProperties("bigpond.config.location");
        BigpondUsageInformation usageInformation = new BigpondConnectorImpl(properties).connect();

        Tablet dialog = new Tablet();
        dialog.setTabletData(usageInformation);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
