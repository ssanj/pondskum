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

import com.googlecode.pondskum.config.ConfigFileLoaderException;

import javax.swing.JFrame;
import java.util.List;
import java.util.concurrent.ExecutionException;

public final class ProgressionSwingWorker extends BigpondSwingWorker {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 115;

    private final JFrame frame;
    private final ProgressionPanel progressionPanel;
    private final ConnectionStatusForm connectionStatus;

    public ProgressionSwingWorker(final JFrame frame) {
        this.frame = frame;
        progressionPanel = new ProgressionPanel();
        connectionStatus = new ConnectionStatusForm();
        frame.getContentPane().add(connectionStatus.getContentPanel());
    }

    @Override
    protected void preConnect() {
        //do nothing.
    }

    @Override
    protected void postConnect() {
        //do nothing.
    }

    @Override
    protected void process(final List<String> statusList) {
        for (String status : statusList) {
            connectionStatus.setProgress(status);//send updates to the connectionStatus form.
        }
    }

    @Override
    protected void done() {
        try {
            hideStatus();

            if (!isCancelled()) {
                showUsage();
                return;
            }

            showError();
        } catch (Exception e) {
            setException(e);
            showError();
        }
    }

    private void hideStatus() {
        frame.getContentPane().remove(connectionStatus.getContentPanel());
    }

    private void showError() {
        String errorMessage = getSimpleExceptionMessage();
        ErrorPanel errorPanel = new ErrorPanel(new DefaultDisplayDetailsPack(), errorMessage);
        //TODO: This is a hack. Find a better way to do this.
        errorPanel.showSeeLogsMessage(!ConfigFileLoaderException.class.isAssignableFrom(getException().getClass()));
        frame.getContentPane().add(errorPanel.getContentPanel());
        frame.setSize(WIDTH, HEIGHT);
        frame.getContentPane().validate();

        notifyFailureListeners(); //kill timers etc.
    }

    private void showUsage() throws InterruptedException, ExecutionException {
        frame.getContentPane().add(progressionPanel.getContentPanel());
        progressionPanel.setUsageInfo(get());
    }
}
