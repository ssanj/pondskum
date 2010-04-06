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

import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.gui.swing.tablet.Tablet;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.event.MouseListener;

import static com.googlecode.pondskum.gui.swing.suite.ContextMenuActions.createContextMenuPopup;

public final class ProgressionTabletGui implements GUI {

    private final Tablet parentDialog;
    private BigpondUsageInformation bigpondUsageInformation;
    private String currentStatus;
    private JPopupMenu contextMenu;
    private MouseListener contextMenuListener;

    public ProgressionTabletGui(final Tablet parentDialog) {
        this.parentDialog = parentDialog;
        resetForReuse();
    }

    @Override
    public void resetForReuse() {
        parentDialog.resetForReuse();
        removeListeners();

        contextMenu = null;
        bigpondUsageInformation = null;
        currentStatus = "";
    }

    private void removeListeners() {
        parentDialog.removeMouseListener(contextMenuListener);
    }

    private void createContextMenu(final StateChangeListener stateChangeListener) {
        contextMenu = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Minimize to tray");
        menuItem.addActionListener(ContextMenuActions.createMinimizeToTrayTransition(this, stateChangeListener));
        contextMenu.add(menuItem);
        menuItem = new JMenuItem("Show Pondskum Bar");
        menuItem.addActionListener(ContextMenuActions.createBarTransition(this, stateChangeListener));
        contextMenu.add(menuItem);
    }

    @Override
    public void display() {
        parentDialog.setVisible(true);
    }

    @Override
    public void dispose() {
        parentDialog.setVisible(false);
        resetForReuse();
    }

    @Override
    public void setStateChangeListener(final StateChangeListener stateChangeListener) {
        createContextMenu(stateChangeListener);
        contextMenuListener = createContextMenuPopup(contextMenu, parentDialog);
        parentDialog.addMouseListener(contextMenuListener);
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
            parentDialog.setTabletData(bigpondUsageInformation);
        }
    }

    @Override
    public void updateWithCurrentStatus(final String currentStatus) {
        this.currentStatus = currentStatus;
        updateConsoleWithStatus(currentStatus);
    }

    @Override
    public void notifyStatusChange(final String status) {
        updateConsoleWithStatus(status);
    }

    @Override
    public void connectionSucceeded(final BigpondUsageInformation bigpondUsageInformation) {
        updateWithExistingUsage(bigpondUsageInformation);
    }

    @Override
    public void connectionFailed(final Exception exception) {
        updateConsoleWithStatus(exception.getMessage());
    }

    private void updateConsoleWithStatus(String status) {
        parentDialog.updateStatus(status);
    }
}
