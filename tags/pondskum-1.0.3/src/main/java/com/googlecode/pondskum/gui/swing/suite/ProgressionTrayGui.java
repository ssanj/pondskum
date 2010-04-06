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
package com.googlecode.pondskum.gui.swing.suite;

import com.googlecode.pinthura.util.SystemPropertyRetrieverImpl;
import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.gui.swing.notifyer.ConnectingTrayNotification;
import com.googlecode.pondskum.gui.swing.notifyer.DefaultTrayNotification;
import com.googlecode.pondskum.gui.swing.notifyer.ProgressTrayIconInstallationException;
import com.googlecode.pondskum.gui.swing.notifyer.RollingTrayNotification;
import com.googlecode.pondskum.gui.swing.notifyer.TrayNotification;
import com.googlecode.pondskum.gui.swing.notifyer.tray.DefaultQuotaFormatter;
import com.googlecode.pondskum.gui.swing.notifyer.tray.UsageFormatter;
import com.googlecode.pondskum.util.DefaultImageLoader;
import com.googlecode.pondskum.util.ImageLoader;

import javax.swing.JOptionPane;
import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.Calendar;

public final class ProgressionTrayGui implements GUI {

    private final TrayIcon trayIcon;
    private final TrayNotification successfulTrayNotification;
    private final TrayNotification unsuccessfulTrayNotification;
    private final RollingTrayNotification connectingTrayNotification;
    private ActionListener trayIconActionListener;
    private BigpondUsageInformation bigpondUsageInformation;
    private MouseMotionListener mouseMotionListener;
    private UsageFormatter usageFormatter;
    private String currentStatus;
    private PopupMenu contextMenu;

    public ProgressionTrayGui(final TrayIcon trayIcon) {
        this.trayIcon = trayIcon;

        ImageLoader imageLoader = new DefaultImageLoader(getClass());
        successfulTrayNotification = new DefaultTrayNotification("Connection Successful", imageLoader.getImage("pondksum.png"));
        unsuccessfulTrayNotification = new DefaultTrayNotification("Connection Information",
                imageLoader.getImage("connection_failed.png"));
        connectingTrayNotification = new ConnectingTrayNotification();
        usageFormatter = new UsageFormatter(new DefaultQuotaFormatter());

        resetForReuse();
    }

    @Override
    public void resetForReuse() {
        removeTrayIcon();
        removeListeners();
        removeContextMenu();

        contextMenu = null;
        trayIconActionListener = null;
        mouseMotionListener = null;
        bigpondUsageInformation = null;
        currentStatus = "";
    }

    private void removeContextMenu() {
        trayIcon.setPopupMenu(null);
    }

    @Override
    public void display() {
        try {
            addTrayIcon();
        } catch (Exception e) {
            handleTrayInstallationError(e);
        }
    }

    private void handleTrayInstallationError(final Exception e) {
        JOptionPane.showMessageDialog(null, "There was an error creating the tray icon.\nPondskum will now exit.",
                "TrayIcon Installation Error", JOptionPane.ERROR_MESSAGE);
        throw new ProgressTrayIconInstallationException(e);
    }

    private void removeTrayIcon() {
        try {
            SystemTray.getSystemTray().remove(trayIcon);
        } catch (Exception e) {
            handleTrayInstallationError(e);
        }
    }

    @Override
    public void dispose() {
        resetForReuse();
    }

    private void removeListeners() {
        trayIcon.removeActionListener(trayIconActionListener);
        trayIcon.removeMouseMotionListener(mouseMotionListener);
    }

    @Override
    public void setStateChangeListener(final StateChangeListener stateChangeListener) {
        createContextMenu(stateChangeListener);
    }

    private void createContextMenu(final StateChangeListener stateChangeListener) {
        contextMenu = new PopupMenu();
        MenuItem menuItem = new MenuItem("Show Pondskum Bar");
        menuItem.addActionListener(ContextMenuActions.createBarTransition(this, stateChangeListener));
        contextMenu.add(menuItem);
        menuItem = new MenuItem("Show Pondskum Tablet");
        menuItem.addActionListener(ContextMenuActions.createTabletTransition(this, stateChangeListener));
        contextMenu.add(menuItem);
        contextMenu.addSeparator();
        menuItem = new MenuItem("Exit");
        menuItem.addActionListener(ContextMenuActions.createExitTransition());
        contextMenu.add(menuItem);
        trayIcon.setPopupMenu(contextMenu);
    }

    @Override
    public BigpondUsageInformation getUsageInfo() {
        return bigpondUsageInformation;
    }

    @Override
    public String getCurrentStatus() {
        return currentStatus;
    }

    @Override
    public void updateWithExistingUsage(final BigpondUsageInformation bigpondUsageInformation) {
        if (bigpondUsageInformation != null) {
            this.bigpondUsageInformation = bigpondUsageInformation;

            trayIcon.displayMessage("Usage Information:", getUsageInfo(bigpondUsageInformation), TrayIcon.MessageType.INFO);
            updateNotification(successfulTrayNotification);
        }
    }

    @Override
    public void updateWithCurrentStatus(final String currentStatus) {
        notifyStatusChange(currentStatus);
    }

    private void addTrayIcon() throws AWTException {
        SystemTray.getSystemTray().add(trayIcon);
        mouseMotionListener = new TrayIconMouseMotionListener();
        trayIcon.addMouseMotionListener(mouseMotionListener);
    }

    @Override
    public void notifyStatusChange(final String status) {
        currentStatus = status;
        updateNotification(connectingTrayNotification.getNotification(status));
    }

    private void updateNotification(final TrayNotification notification) {
        trayIcon.setImage(notification.getImage());
        trayIcon.setToolTip(notification.getMessage());
    }

    @Override
    public void connectionSucceeded(final BigpondUsageInformation bigpondUsageInformation) {
        trayIcon.displayMessage("Connection Successful", createUpdatedStatus(bigpondUsageInformation), TrayIcon.MessageType.INFO);
        updateNotification(successfulTrayNotification);
    }

    private String createUpdatedStatus(final BigpondUsageInformation bigpondUsageInformation) {
        return new StringBuilder().
                append(getUpdatedTime()).
                append(new SystemPropertyRetrieverImpl().getLineSeparator()).
                append(getUsageInfo(bigpondUsageInformation)).toString();
    }

    private String getUpdatedTime() {
        return String.format("Usage information updated @ %1$tA %1$tB %1$te %1$tY @ %1$tl:%1$tM %1$Tp",
                Calendar.getInstance().getTimeInMillis());
    }

    private String getUsageInfo(BigpondUsageInformation bigpondUsageInformation) {
        this.bigpondUsageInformation = bigpondUsageInformation;
        return usageFormatter.formatUsage(bigpondUsageInformation);
    }

    @Override
    public void connectionFailed(final Exception exception) {
        trayIcon.displayMessage("Connection Information", exception.getMessage(), TrayIcon.MessageType.ERROR);
        updateNotification(unsuccessfulTrayNotification);
    }

    private class TrayIconMouseMotionListener extends MouseMotionAdapter {

        @Override public void mouseMoved(final MouseEvent e) {
            if (bigpondUsageInformation != null) {
                trayIcon.setToolTip(getUsageInfo(bigpondUsageInformation));
            }
        }
    }
}
