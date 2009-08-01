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

import javax.swing.ImageIcon;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Installs the Progression tray icon. All updates are handed over to the <code>UsageTooltipProvider</code>interface.
 */
public final class ProgressTrayIcon {

    private boolean installed;

    /**
     * This method must be called before {@link #install}.
     * Returns true if this tray icon can be installed, false otherwise.
     * @return true if this tray icon can be installed, false otherwise.
     */
    public boolean canInstall() {
        return SystemTray.isSupported();
    }

    public void install(TooltipUsageUpdater usageUpdater) {
        if (!installed) { //install this only once.
            final SystemTray systemTray = SystemTray.getSystemTray();
            try {
                TrayIcon trayIcon = createTrayIcon(systemTray, usageUpdater);
                systemTray.add(trayIcon);
                installed = true;
            } catch (Exception e) {
                throw new ProgressTrayIconInstallationException("Could not install tray icon. See logs for details.", e);
            }
        }
    }

    private TrayIcon createTrayIcon(final SystemTray systemTray, final TooltipUsageUpdater usageUpdater) {
        ImageIcon image = new ImageIcon(getClass().getResource("/com/googlecode/pondskum/gui/images/map.png"));
        final TrayIcon trayIcon = new TrayIcon(image.getImage());
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(new TrayIconActionListener(systemTray, trayIcon));
        trayIcon.addMouseMotionListener(new TrayIconMouseMotionListener(trayIcon, usageUpdater));
        return trayIcon;
    }

    private static class TrayIconActionListener implements ActionListener {

        private final SystemTray systemTray;
        private final TrayIcon trayIcon;

        private TrayIconActionListener(final SystemTray systemTray, final TrayIcon trayIcon) {
            this.systemTray = systemTray;
            this.trayIcon = trayIcon;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            System.out.println("closing down");
            systemTray.remove(trayIcon);
            System.exit(0);
        }
    }

    private static final class TrayIconMouseMotionListener implements MouseMotionListener {

        private final TrayIcon trayIcon;
        private TooltipUsageUpdater usageUpdater;

        private TrayIconMouseMotionListener(final TrayIcon trayIcon, final TooltipUsageUpdater usageUpdater) {
            this.trayIcon = trayIcon;
            this.usageUpdater = usageUpdater;
        }

        @Override
        public void mouseDragged(final MouseEvent e) {
            //do nothing.
        }

        @Override
        public void mouseMoved(final MouseEvent e) {
            usageUpdater.updateTooltip(trayIcon);
        }
    }
}
