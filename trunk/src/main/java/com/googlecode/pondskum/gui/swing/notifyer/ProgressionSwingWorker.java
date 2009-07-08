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

import com.googlecode.pinthura.util.SystemPropertyRetrieverImpl;
import com.googlecode.pondskum.client.BigpondConnector;
import com.googlecode.pondskum.client.BigpondConnectorImpl;
import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.config.ConfigFileLoaderException;
import com.googlecode.pondskum.config.ConfigFileLoaderImpl;
import com.googlecode.pondskum.gui.swing.tablet.ConsoleConnectionListener;
import com.googlecode.pondskum.gui.swing.tablet.StatusUpdatable;

import javax.swing.JFrame;
import javax.swing.SwingWorker;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public final class ProgressionSwingWorker extends SwingWorker<BigpondUsageInformation, String> implements StatusUpdatable {

    private Exception exception;
    private final JFrame frame;
    private final ProgressionPanel progressionPanel;

    public ProgressionSwingWorker(final JFrame frame, final ProgressionPanel progressionPanel) {
        this.frame = frame;
        this.progressionPanel = progressionPanel;
    }

    @Override
    protected BigpondUsageInformation doInBackground() throws Exception {
        try {
            Properties properties = new ConfigFileLoaderImpl(new SystemPropertyRetrieverImpl()).loadProperties("bigpond.config.location");
            BigpondConnector bigpondConnector = new BigpondConnectorImpl(properties);
            return bigpondConnector.connect(new ConsoleConnectionListener(this));
        } catch (Exception e) {
            exception = e;
            cancel(true);
            return null;
        }
    }

    private String getSimpleMessage(final Exception e) {
        int messageIndex = e.getMessage().indexOf(':');
        if (messageIndex != -1 && messageIndex++ <= e.getMessage().length()) {
            return e.getMessage().substring(messageIndex);
        }

        return e.getMessage();
    }

    @Override
    protected void process(final List<String> statusList) {
        for (String status : statusList) {
            progressionPanel.getLastUpdatedLabel().setText(status);
        }
    }

    @Override
    public void updateStatus(final String update) {
        publish(update);
    }

    @Override
    protected void done() {
        try {

            if (!isCancelled()) {
                showUsage();
                return;
            }

            showError();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showError() {
        String errorMessage = getSimpleMessage(exception);
        ErrorPanel errorPanel = new ErrorPanel(new DefaultDisplayDetailsPack(), errorMessage);
        errorPanel.showSeeLogsMessage(!ConfigFileLoaderException.class.isAssignableFrom(exception.getClass()));
        frame.getContentPane().remove(progressionPanel.getContentPanel());
        frame.getContentPane().add(errorPanel.getContentPanel());
        frame.setSize(600, 115);
    }

    private void showUsage() throws InterruptedException, ExecutionException {
        progressionPanel.setUsageInfo(get());
        //        frame.setSize(600, 90);
//        frame.setVisible(true);
//        frame.setLocationRelativeTo(null);
    }

}
