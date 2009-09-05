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

import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.config.Config;
import com.googlecode.pondskum.config.DefaultConfigLoader;
import com.googlecode.pondskum.logger.LogProvider;
import com.googlecode.pondskum.timer.DefaultTimer;
import com.googlecode.pondskum.timer.RepeatFrequency;
import com.googlecode.pondskum.timer.SimpleTimer;
import com.googlecode.pondskum.timer.TimerDelay;

import javax.swing.JFrame;
import javax.swing.SwingWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public final class ProgressionPanelRunner {

    private ProgressionPanelRunner() {
        //runner.
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("Progression");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Config config = new DefaultConfigLoader().loadConfig();
        createTimer(frame, config);
        frame.setSize(600, 90);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //TODO: Move to a builder.
    private static void createTimer(final JFrame frame, final Config config) {
        LogProvider logProvider = config.getLogProvider();
        BigpondSwingWorker worker = new ProgressionSwingWorker(frame, logProvider);
        SimpleTimer timer = new DefaultTimer(new TimedAction(worker, logProvider), getTimerDelayInMilliSeconds(config, logProvider));
        worker.addFailureListener(new TimerStopper(timer, logProvider));
        timer.start();
    }

    private static int getTimerDelayInMilliSeconds(final Config config, final LogProvider logProvider) {
        RepeatFrequency timerDelay = new TimerDelay(config);
        Logger logger = logProvider.provide(ProgressionPanelRunner.class);
        logger.info("Using default timer delay of " + timerDelay.getFrequencyInMinutes() + " minutes.");
        return timerDelay.getFrequencyInMilliSeconds();
    }

    private static final class TimedAction implements ActionListener {

        private SwingWorker<BigpondUsageInformation, String> swingWorker;
        private Logger logger;

        public TimedAction(final SwingWorker<BigpondUsageInformation, String> swingWorker, final LogProvider logProvider) {
            this.swingWorker = swingWorker;
            logger = logProvider.provide(getClass());
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            logger.info("Updating information...");
            swingWorker.execute();
        }
    }
}
