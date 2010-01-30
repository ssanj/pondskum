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
import com.googlecode.pondskum.config.ConfigFileLoaderException;
import com.googlecode.pondskum.gui.swing.notifyer.ConnectionStatusForm;
import com.googlecode.pondskum.gui.swing.notifyer.DefaultDisplayDetailsPack;
import com.googlecode.pondskum.gui.swing.notifyer.ErrorPanel;
import com.googlecode.pondskum.gui.swing.notifyer.ProgressionPanel;

import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static com.googlecode.pondskum.gui.swing.suite.DefaultGuiFactory.PROGRESSION_HEIGHT;
import static com.googlecode.pondskum.gui.swing.suite.DefaultGuiFactory.PROGRESSION_WIDTH;

public final class ProgressionGui implements GUI {

    private ProgressionPanel progressionPanel;
    private ConnectionStatusForm connectionStatusForm;
    private JFrame parentFrame;
    private WindowListener windowListener;
    private MouseListener mouseListener;
    private BigpondUsageInformation bigpondUsageInformation;
    private String currentStatus;

    public ProgressionGui(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        resetForReuse();
    }

    @Override
    public void resetForReuse() {
        progressionPanel = new ProgressionPanel();
        connectionStatusForm = new ConnectionStatusForm();
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(connectionStatusForm.getContentPanel());
        removeListener();

        windowListener = null;
        mouseListener = null;
        bigpondUsageInformation = null;
        currentStatus = "";
    }

    private void removeListener() {
        parentFrame.removeWindowListener(windowListener);
        progressionPanel.getContentPanel().removeMouseListener(mouseListener);
    }

    public void display() {
        parentFrame.setVisible(true);
    }

    @Override
    public void dispose() {
        parentFrame.setVisible(false);
        resetForReuse();
    }

    @Override
    public void setStateChangeListener(final StateChangeListener stateChangeListener) {
        windowListener = new ProgressionBarWindowStateListener(stateChangeListener);
        mouseListener = new ProgressionBarMouseListener(stateChangeListener);
        parentFrame.addWindowListener(windowListener);
        progressionPanel.getContentPanel().addMouseListener(mouseListener);
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
            updateWithUsageInformation();
            parentFrame.getContentPane().validate();
        }
    }

    @Override
    public void updateWithCurrentStatus(final String currentStatus) {
        notifyStatusChange(currentStatus);
    }

    @Override
    public void notifyStatusChange(final String status) {
       currentStatus = status;
       connectionStatusForm.setProgress(status);
    }

    @Override
    public void connectionSucceeded(final BigpondUsageInformation bigpondUsageInformation) {
        this.bigpondUsageInformation = bigpondUsageInformation;
        hideStatusForm();
        updateWithUsageInformation();
    }

    private void updateWithUsageInformation() {
        parentFrame.getContentPane().add(progressionPanel.getContentPanel());
        progressionPanel.setUsageInfo(bigpondUsageInformation);
    }

    private void hideStatusForm() {
        parentFrame.getContentPane().remove(connectionStatusForm.getContentPanel());
    }

    @Override
    public void connectionFailed(final Exception exception) {
        hideStatusForm();

        String errorMessage = exception.getMessage();
        ErrorPanel errorPanel = new ErrorPanel(new DefaultDisplayDetailsPack(), errorMessage);
        errorPanel.showSeeLogsMessage(!ConfigFileLoaderException.class.isAssignableFrom(exception.getClass()));
        parentFrame.getContentPane().add(errorPanel.getContentPanel());
        parentFrame.setSize(PROGRESSION_WIDTH, PROGRESSION_HEIGHT);
        parentFrame.getContentPane().validate();
    }

    private class ProgressionBarWindowStateListener extends WindowAdapter {

        private final StateChangeListener stateChangeListener;

        public ProgressionBarWindowStateListener(final StateChangeListener stateChangeListener) {
            this.stateChangeListener = stateChangeListener;
        }

        @Override
        public void windowIconified(final WindowEvent e) {
            stateChangeListener.stateChangeOccured(ProgressionGui.this, GuiEnumeration.PROGRESS_TRAY);
        }
    }

    private class ProgressionBarMouseListener extends MouseAdapter {

        private final StateChangeListener stateChangeListener;

        public ProgressionBarMouseListener(final StateChangeListener stateChangeListener) {
            this.stateChangeListener = stateChangeListener;
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
            if (e.getClickCount() == 2) {
                stateChangeListener.stateChangeOccured(ProgressionGui.this, GuiEnumeration.PROGRESSION_TABLET);
            }
        }
    }
}
