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
import com.googlecode.pondskum.config.Config;
import com.googlecode.pondskum.config.ConfigFileLoaderException;
import com.googlecode.pondskum.gui.swing.notifyer.ConnectionStatusForm;
import com.googlecode.pondskum.gui.swing.notifyer.DefaultDisplayDetailsPack;
import com.googlecode.pondskum.gui.swing.notifyer.ErrorPanel;
import com.googlecode.pondskum.gui.swing.notifyer.ProgressionPanel;

import javax.swing.JFrame;

public final class ProgressionGui implements GUI {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 90;

    private JFrame frame;
    private ProgressionPanel progressionPanel;
    private ConnectionStatusForm connectionStatusForm;

    public ProgressionGui() {
        createFrame();
        setupPanels();
    }

    private void createFrame() {
        frame = new JFrame("Progression");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
    }

    private void setupPanels() {
        progressionPanel = new ProgressionPanel();
        connectionStatusForm = new ConnectionStatusForm();
        frame.getContentPane().add(connectionStatusForm.getContentPanel());
    }

    public void display(final Config config) {
        frame.setVisible(true);
    }

    @Override
    public void notifyStatusChange(final String status) {
       connectionStatusForm.setProgress(status);
    }

    private void hideStatusForm() {
        frame.getContentPane().remove(connectionStatusForm.getContentPanel());
    }

    @Override
    public void connectionSucceeded(final BigpondUsageInformation bigpondUsageInformation) {
        hideStatusForm();

        frame.getContentPane().add(progressionPanel.getContentPanel());
        progressionPanel.setUsageInfo(bigpondUsageInformation);
    }

    @Override
    public void connectionFailed(final Exception exception) {
        hideStatusForm();

        String errorMessage = exception.getMessage();
        ErrorPanel errorPanel = new ErrorPanel(new DefaultDisplayDetailsPack(), errorMessage);
        errorPanel.showSeeLogsMessage(!ConfigFileLoaderException.class.isAssignableFrom(exception.getClass()));
        frame.getContentPane().add(errorPanel.getContentPanel());
        frame.setSize(WIDTH, HEIGHT);
        frame.getContentPane().validate();
    }
}
