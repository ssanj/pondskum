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
package com.googlecode.pondskum.gui.swing.notifyer;

import java.util.Calendar;
import java.util.List;

public final class ProgressTrayIconSwingWorker extends BigpondSwingWorker {

    private ProgressTrayIcon progressTrayIcon;
    private BigpondTrayIconUsageUpdater usageUpdater;

    public ProgressTrayIconSwingWorker() {
        progressTrayIcon = new ProgressTrayIcon();
        if (progressTrayIcon.canInstall()) {
            usageUpdater = new BigpondTrayIconUsageUpdater();
            progressTrayIcon.install(usageUpdater);
            return;
        }

        throw new ProgressTrayIconInstallationException("TrayIcon is not supported on this platform.");
    }

    @Override
    protected void postConnect() {
        //done nothing special.
    }

    @Override
    protected void process(final List<String> statusList) {
        for (String status: statusList) {
            usageUpdater.setTooltip(status);
        }
    }

    @Override
    protected void done() {
        if (!isCancelled()) {
            usageUpdater.showSuccessMessage(getSuccessMessage());
            usageUpdater.setBigpondUsageInformation(getUsageInformation());
            return;
        }

        usageUpdater.showErrorMessage(getSimpleExceptionMessage(getException())); //simplify this.
    }

    private String getSuccessMessage() {
        return String.format("Usage information updated @ %1$tA %1$tB %1$te %1$tY @ %1$tl:%1$tM %1$Tp",
                Calendar.getInstance().getTimeInMillis());
    }
}
