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

import java.awt.TrayIcon;

/**
 * Updates the tray icon tooltip with usage information and displays messages about connection statuses.
 */
public interface TrayIconUsageUpdater {

    /**
     * This has to be set before any of the other methods are invoked.
     * Updates the supplied <code>TrayIcon</code> with information via tooltips and messages.
     * @param trayIcon The <code>TrayIcon</code> to update.
     */
    void setTrayIcon(TrayIcon trayIcon);

    /**
     * Called from the <code>TrayIcon</code> to get updated tooltip information.
     * Eg. if the user hovers over the tray icon then calling this method will update the tooltip of the tray icon.
     */
    void updateTooltip();

    /**
     * Shows an information message over the tray icon.
     * @param message The message to display.
     */
    void setTooltip(String message);

    /**
     * Changes the tooltip of the the tray icon and/or changes its image.
     */
    void showBusyMessage(String message);

    /**
     * Shows an error message over the tray icon.
     * @param message The message to display.
     */
    void showErrorMessage(String message);

    /**
     * Shows a successful connection update over the tray icon.
     * @param message The message to display.
     */
    void showSuccessMessage(String message);
}
