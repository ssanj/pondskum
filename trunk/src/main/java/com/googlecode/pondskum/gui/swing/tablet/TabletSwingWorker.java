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
package com.googlecode.pondskum.gui.swing.tablet;

import com.googlecode.pondskum.client.BigpondConnector;
import com.googlecode.pondskum.client.BigpondConnectorImpl;
import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.config.ConfigFileLoaderImpl;
import com.googlecode.pondskum.config.SystemPropertyRetrieverImpl;

import javax.swing.SwingWorker;
import java.util.List;
import java.util.Properties;

public final class TabletSwingWorker extends SwingWorker<BigpondUsageInformation, String> implements Updatable {

    private final Tablet tablet;

    TabletSwingWorker(final Tablet tablet) {
        this.tablet = tablet;
    }

    @Override
    protected BigpondUsageInformation doInBackground() throws Exception {
        Properties properties = new ConfigFileLoaderImpl(new SystemPropertyRetrieverImpl()).loadProperties("bigpond.config.location");
        BigpondConnector bigpondConnector = new BigpondConnectorImpl(properties);
        return bigpondConnector.connect(new ConsoleConnectionListener(this));
    }

    @Override
    protected void process(final List<String> updateList) {
        for (String update : updateList) {
            tablet.update(update);
        }
    }

    @Override
    protected void done() {
        try {
            tablet.setTabletData(get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(final String update) {
        publish(update);
    }
}
