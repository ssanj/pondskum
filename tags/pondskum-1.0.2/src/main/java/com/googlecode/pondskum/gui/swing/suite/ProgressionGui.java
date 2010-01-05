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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public final class ProgressionGui implements GUI {

    static final int WIDTH = 600;
    static final int HEIGHT = 90;

    private ProgressionPanel progressionPanel;
    private ConnectionStatusForm connectionStatusForm;
    private JFrame parentFrame;
    private WindowListener windowListener;
    private BigpondUsageInformation bigpondUsageInformation;

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
        removeWindowListener();

        windowListener = null;
        bigpondUsageInformation = null;
    }

    private void removeWindowListener() {
        parentFrame.removeWindowListener(windowListener);
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
        parentFrame.addWindowListener(windowListener);
    }

    @Override
    public BigpondUsageInformation getUsageInfo() {
        return bigpondUsageInformation;
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
    public void notifyStatusChange(final String status) {
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
        parentFrame.setSize(WIDTH, HEIGHT);
        parentFrame.getContentPane().validate();
    }

    private class ProgressionBarWindowStateListener extends WindowAdapter {

        private final StateChangeListener stateChangeListener;

        public ProgressionBarWindowStateListener(final StateChangeListener stateChangeListener) {
            this.stateChangeListener = stateChangeListener;
        }

        @Override
        public void windowIconified(final WindowEvent e) {
            stateChangeListener.stateChangeOccured(ProgressionGui.this);
        }
    }
}