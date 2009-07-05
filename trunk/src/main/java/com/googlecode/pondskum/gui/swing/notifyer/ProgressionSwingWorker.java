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
import com.googlecode.pondskum.config.ConfigFileLoaderImpl;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public final class ProgressionSwingWorker extends SwingWorker<BigpondUsageInformation, String> {

    private String errorMessage;

    @Override
    protected BigpondUsageInformation doInBackground() throws Exception {
        try {
            Properties properties = new ConfigFileLoaderImpl(new SystemPropertyRetrieverImpl()).loadProperties("bigpond.config.location");
            BigpondConnector bigpondConnector = new BigpondConnectorImpl(properties);
            return bigpondConnector.connect();
        } catch (Exception e) {
            errorMessage = getSimpleMessage(e);
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
    protected void done() {
        try {
            JFrame f = new JFrame("Progression");
            f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            if (!isCancelled()) {
                showUsage(f);
                return;
            }

            showError(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showError(final JFrame f) {
        f.getContentPane().add(createErrorPanel());
        f.setSize(600, 115);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }

    private void showUsage(final JFrame f) throws InterruptedException, ExecutionException {
        f.getContentPane().add(createProgressionPanel());
        f.setSize(600, 90);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }

    private JPanel createProgressionPanel() throws InterruptedException, ExecutionException {
        return new ProgressionPanel(get()).getContentPanel();
    }

    private JPanel createErrorPanel() {
        return new ErrorPanel(new DefaultDisplayDetailsPack(), errorMessage).getContentPanel();
    }
}
