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
import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.config.ConfigFileLoaderImpl;
import com.googlecode.pondskum.config.ConfigurationEnum;
import com.googlecode.pondskum.logger.DefaultLogProvider;
import com.googlecode.pondskum.logger.LogProvider;
import com.googlecode.pondskum.timer.DefaultTimer;
import com.googlecode.pondskum.timer.RepeatFrequency;
import com.googlecode.pondskum.timer.SimpleTimer;
import com.googlecode.pondskum.timer.TimerDelay;

import javax.swing.JFrame;
import javax.swing.SwingWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.logging.Logger;

public final class ProgressionPanelRunner {

    private ProgressionPanelRunner() {
        //runner.
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("Progression");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Properties userProperties = getUserProperties();
        LogProvider logProvider = new DefaultLogProvider(userProperties.getProperty(ConfigurationEnum.LOG_FILE.getKey()));
        createTimer(frame, logProvider);
        frame.setSize(600, 90);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //TODO: Move to a builder.
    private static void createTimer(final JFrame frame, final LogProvider logProvider) {
        BigpondSwingWorker worker = new ProgressionSwingWorker(frame, logProvider);
        SimpleTimer timer = new DefaultTimer(new TimedAction(worker, logProvider), getTimerDelayInMilliSeconds());
        worker.addFailureListener(new TimerStopper(timer, logProvider));
        timer.start();
    }

    private static int getTimerDelayInMilliSeconds() {
        Properties userProperties = getUserProperties();
        RepeatFrequency timerDelay = new TimerDelay(userProperties);
        //TODO: use logger.
        System.out.println("Using default timer delay of " + timerDelay.getFrequencyInMinutes() + " minutes.");
        return timerDelay.getFrequencyInMilliSeconds();
    }

    //TODO: Move this to a configuration class and reuse across the project.
    private static Properties getUserProperties() {
        return new ConfigFileLoaderImpl(new SystemPropertyRetrieverImpl()).loadProperties(getPropertyFileLocationSystemPropertyKey());
    }

    private static String getPropertyFileLocationSystemPropertyKey() {
        return ConfigurationEnum.CONFIG_FILE_LOCATION.getKey();
    }

    private static final class TimedAction implements ActionListener {

        private SwingWorker<BigpondUsageInformation, String> swingWorker;
        private Logger logger;

        private TimedAction(final SwingWorker<BigpondUsageInformation, String> swingWorker, final LogProvider logProvider) {
            this.swingWorker = swingWorker;
            logger = logProvider.provide(getClass());
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            logger.info("Updating information...");
            swingWorker.execute();
        }
    }

    private static final class TimerStopper implements ConnectionFailureListener {

        private final SimpleTimer simpleTimer;
        private Logger logger;

        private TimerStopper(final SimpleTimer simpleTimer, final LogProvider logProvider) {
            this.simpleTimer = simpleTimer;
            logger = logProvider.provide(getClass());
        }

        @Override
        public void connectionFailed() {
            logger.info("Timer stopping...");
            simpleTimer.stop();
            logger.info("Timer stopped.");
        }
    }

}
