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

import com.googlecode.pondskum.client.BigpondUsageInformation;

import java.awt.TrayIcon;

public final class BigpondTooltipUsageUpdater implements TooltipUsageUpdater {

    private BigpondUsageInformation bigpondUsageInformation;

    public BigpondUsageInformation getBigpondUsageInformation() {
        return bigpondUsageInformation;
    }

    public void setBigpondUsageInformation(final BigpondUsageInformation bigpondUsageInformation) {
        this.bigpondUsageInformation = bigpondUsageInformation;
    }

    @Override
    public void updateTooltip(final TrayIcon trayIcon) {
        String message = createUsageMessage();
        trayIcon.setToolTip(message);
    }

    private String createUsageMessage() {
        return new StringBuilder().append("[").
                append(bigpondUsageInformation.getAccountInformation().getAccountName()).append(" - ").
                append(bigpondUsageInformation.getTotalUsage().getUploadUsage()).append("/").
                append(bigpondUsageInformation.getTotalUsage().getDownloadUsage()).append("/").
                append(bigpondUsageInformation.getTotalUsage().getTotalUsage()).
                append("]").toString();
    }
}
