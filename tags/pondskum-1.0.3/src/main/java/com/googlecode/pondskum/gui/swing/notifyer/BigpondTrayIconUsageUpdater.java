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
import com.googlecode.pondskum.gui.swing.notifyer.tray.DefaultQuotaFormatter;
import com.googlecode.pondskum.gui.swing.notifyer.tray.UsageFormatter;
import com.googlecode.pondskum.util.DefaultImageLoader;
import com.googlecode.pondskum.util.ImageLoader;

import java.awt.TrayIcon;

public final class BigpondTrayIconUsageUpdater implements TrayIconUsageUpdater {

    private BigpondUsageInformation bigpondUsageInformation;
    private TrayIcon trayIcon;
    private TrayNotification successfulTrayNotification;
    private TrayNotification unsuccessfulTrayNotification;
    private RollingTrayNotification connectingTrayNotification;
    private UsageFormatter usageFormatter;

    public BigpondTrayIconUsageUpdater() {
        ImageLoader imageLoader = new DefaultImageLoader(getClass());
        successfulTrayNotification = new DefaultTrayNotification("Connection Successful", imageLoader.getImage("pondksum.png"));
        unsuccessfulTrayNotification = new DefaultTrayNotification("Connection Information",
                imageLoader.getImage("connection_failed.png"));
        connectingTrayNotification = new ConnectingTrayNotification();
        usageFormatter = new UsageFormatter(new DefaultQuotaFormatter());
    }

    public void setBigpondUsageInformation(final BigpondUsageInformation bigpondUsageInformation) {
        this.bigpondUsageInformation = bigpondUsageInformation;
        setTooltip(createUsageMessage());
    }

    @Override
    public void updateTooltip() {
        setTooltip(createUsageMessage());
    }

    @Override
    public void setTooltip(final String message) {
        trayIcon.setToolTip(message);
    }

    @Override
    public void showBusyMessage(final String message) {
        updateNotification(connectingTrayNotification.getNotification(message));
    }

    @Override
    public void showSuccessMessage(String message) {
        trayIcon.displayMessage("Connection Successful", message, TrayIcon.MessageType.INFO);
        updateNotification(successfulTrayNotification);
    }

    @Override
    public void showErrorMessage(final String message) {
        trayIcon.displayMessage("Connection Information", message, TrayIcon.MessageType.ERROR);
        updateNotification(unsuccessfulTrayNotification);
    }

    @Override
    public void setTrayIcon(final TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
    }

    private void updateNotification(final TrayNotification notification) {
        trayIcon.setImage(notification.getImage());
        setTooltip(notification.getMessage());
    }

    private String createUsageMessage() {
        return usageFormatter.formatUsage(bigpondUsageInformation);
    }
}
