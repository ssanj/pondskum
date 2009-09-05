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
import com.googlecode.pondskum.gui.swing.notifyer.BigpondSwingWorker;

import java.util.List;

public final class TabletSwingWorker extends BigpondSwingWorker {

    private final UpdatableTablet tablet;
    private StopWatch stopWatch;

    TabletSwingWorker(final UpdatableTablet tablet) {
        this.tablet = tablet;
        stopWatch = new StopWatchBuilder().build();
    }

    @Override
    protected void preConnect() {
        stopWatch.start();
    }

    @Override
    protected void postConnect() {
        tablet.updateStatus("Time taken: " + stopWatch.getTimeInSeconds() + " seconds");
    }

    @Override
    protected void process(final List<String> statusList) {
         for (String update : statusList) {
            tablet.updateStatus(update);
        }
    }

    @Override
    protected void done() {
        if (!isCancelled()) {
            tablet.setTabletData(getUsageInformation());
            return;
        }

        tablet.updateStatus("There was an error retrieving your usage data." + getSimpleExceptionMessage());
        notifyFailureListeners();//notify of the failure.
    }
}
