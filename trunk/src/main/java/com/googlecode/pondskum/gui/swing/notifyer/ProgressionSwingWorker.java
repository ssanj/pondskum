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
import com.googlecode.pondskum.client.listener.DetailedConnectionListener;
import com.googlecode.pondskum.config.ConfigFileLoaderException;
import com.googlecode.pondskum.config.ConfigFileLoaderImpl;
import com.googlecode.pondskum.gui.swing.tablet.ConsoleConnectionListener;
import com.googlecode.pondskum.gui.swing.tablet.StatusUpdatable;
import com.googlecode.pondskum.util.NumericUtil;
import com.googlecode.pondskum.util.NumericUtilImpl;

import javax.swing.JFrame;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public final class ProgressionSwingWorker extends SwingWorker<BigpondUsageInformation, String> implements StatusUpdatable {

    private static final int TEN_MINS = 10; //minimum update interval.
    private static final String UPDATE_INTERVAL_KEY = "update.interval";

    private Exception exception;
    private final JFrame frame;
    private final Timer timer;
    private final ProgressionPanel progressionPanel;
    private final ConnectionStatusForm connectionStatus;
    private final NumericUtil numericUtil;
    private final Logger logger;

    public ProgressionSwingWorker(final JFrame frame, final Timer timer) {
        this.frame = frame;
        this.timer = timer;
        progressionPanel = new ProgressionPanel();
        connectionStatus = new ConnectionStatusForm();
        numericUtil = new NumericUtilImpl();
        // use the same logging strategy as DetailedConnectionListener.
        logger = Logger.getLogger(DetailedConnectionListener.class.getPackage().getName());

        frame.getContentPane().add(connectionStatus.getContentPanel());
    }

    @Override
    protected BigpondUsageInformation doInBackground() throws Exception {
        try {
            Properties properties = new ConfigFileLoaderImpl(new SystemPropertyRetrieverImpl()).loadProperties("bigpond.config.location");
            BigpondConnector bigpondConnector = new BigpondConnectorImpl(properties);
            BigpondUsageInformation usageInformation = bigpondConnector.connect(new ConsoleConnectionListener(this));
            setTimerDelayIfSet(properties);
            return usageInformation;
        } catch (Exception e) {
            exception = e;
            cancel(true);
            return null;
        }
    }

    private void setTimerDelayIfSet(final Properties properties) {
        if (properties.containsKey(UPDATE_INTERVAL_KEY)) {
            String interval = properties.getProperty(UPDATE_INTERVAL_KEY);
            if (numericUtil.isNumber(interval)) {
                timer.setDelay(Math.max(TEN_MINS, numericUtil.getNumber(interval))); //set a minimum of 10 minutes.
                logger.info("Setting timer delay to " + interval + " minutes.");
            }
            return;
        }

        logger.info("Using default timer delay.");
    }


    private String getSimpleMessage(final Exception e) {
//        int messageIndex = e.getMessage().indexOf(':');
//        if (messageIndex != -1 && messageIndex++ <= e.getMessage().length()) {
//            return e.getMessage().substring(messageIndex);
//        }
//
//        return e.getMessage();
        Throwable nested = e;
        while (nested.getCause() != null) {
            nested = nested.getCause();
        }

        return nested.getMessage();

    }

    @Override
    protected void process(final List<String> statusList) {
        for (String status : statusList) {
            connectionStatus.setProgress(status);
        }
    }

    @Override
    public void updateStatus(final String update) {
        publish(update);
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
            exception = e;
            showError();
        }
    }

    private void hideStatus() {
        frame.getContentPane().remove(connectionStatus.getContentPanel());
    }


    private void showError() {
        String errorMessage = getSimpleMessage(exception);
        ErrorPanel errorPanel = new ErrorPanel(new DefaultDisplayDetailsPack(), errorMessage);
        errorPanel.showSeeLogsMessage(!ConfigFileLoaderException.class.isAssignableFrom(exception.getClass()));
        frame.getContentPane().add(errorPanel.getContentPanel());
        frame.setSize(600, 115);
        timer.stop();
    }

    private void showUsage() throws InterruptedException, ExecutionException {
        frame.getContentPane().add(progressionPanel.getContentPanel());
        progressionPanel.setUsageInfo(get());
    }

}
