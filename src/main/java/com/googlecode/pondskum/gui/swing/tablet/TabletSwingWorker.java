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

import com.googlecode.pinthura.util.StopWatch;
import com.googlecode.pinthura.util.builder.StopWatchBuilder;
import com.googlecode.pondskum.client.BigpondConnector;
import com.googlecode.pondskum.client.BigpondConnectorImpl;
import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.config.DefaultConfigLoader;

import javax.swing.SwingWorker;
import java.util.List;

public final class TabletSwingWorker extends SwingWorker<BigpondUsageInformation, String> implements StatusUpdatable {

    private final UpdatableTablet tablet;
    private StopWatch stopWatch;

    TabletSwingWorker(final UpdatableTablet tablet) {
        this.tablet = tablet;
        stopWatch = new StopWatchBuilder().build();
    }

    @Override
    protected BigpondUsageInformation doInBackground() throws Exception {
        tablet.disableUpdates(); //prevent other updates from running simulataneously.        
        BigpondConnector bigpondConnector = new BigpondConnectorImpl(new DefaultConfigLoader().loadConfig());
        stopWatch.start();
        return bigpondConnector.connect(new ConsoleConnectionListener(this));
    }

    @Override
    protected void process(final List<String> updateList) {
        for (String update : updateList) {
            tablet.updateStatus(update);
        }
    }

    @Override
    protected void done() {
        try {
            tablet.setTabletData(get());
            tablet.updateStatus("Time taken: " + stopWatch.getTimeInSeconds() + " seconds");
            tablet.enableUpdates(); //reactivate updates.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatus(final String update) {
        publish(update);
    }
}
