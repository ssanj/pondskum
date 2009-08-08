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
import com.googlecode.pondskum.util.NumericUtil;
import com.googlecode.pondskum.util.NumericUtilImpl;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public final class ProgressionSwingWorker extends BigpondSwingWorker {

    private static final int TEN_MINS = 10; //minimum update interval.
    private static final String UPDATE_INTERVAL_KEY = "update.interval";

    private final JFrame frame;
    private final Timer timer;
    private final ProgressionPanel progressionPanel;
    private final ConnectionStatusForm connectionStatus;
    private final NumericUtil numericUtil;

    public ProgressionSwingWorker(final JFrame frame, final Timer timer) {
        this.frame = frame;
        this.timer = timer;
        progressionPanel = new ProgressionPanel();
        connectionStatus = new ConnectionStatusForm();
        numericUtil = new NumericUtilImpl();
        frame.getContentPane().add(connectionStatus.getContentPanel());
    }

    @Override
    protected void postConnect() {
        setTimerDelayIfSet(getProperties());//set the delay for the next connection, if successfully connected.
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

    private void setTimerDelayIfSet(final Properties properties) {
        if (properties.containsKey(UPDATE_INTERVAL_KEY)) {
            String interval = properties.getProperty(UPDATE_INTERVAL_KEY);
            if (numericUtil.isNumber(interval)) {
                timer.setDelay(Math.max(TEN_MINS, numericUtil.getNumber(interval))); //set a minimum of 10 minutes.
                getLogger().info("Setting timer delay to " + interval + " minutes.");
            }
            return;
        }

        getLogger().info("Using default timer delay.");
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
        frame.setSize(600, 115);
        timer.stop();
    }

    private void showUsage() throws InterruptedException, ExecutionException {
        frame.getContentPane().add(progressionPanel.getContentPanel());
        progressionPanel.setUsageInfo(get());
    }
}
