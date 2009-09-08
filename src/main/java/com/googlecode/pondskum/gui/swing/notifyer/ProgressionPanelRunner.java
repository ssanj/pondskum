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

import com.googlecode.pondskum.config.Config;
import com.googlecode.pondskum.config.DefaultConfigLoader;
import com.googlecode.pondskum.logger.LogProvider;
import com.googlecode.pondskum.timer.DefaultTimer;
import com.googlecode.pondskum.timer.RepeatFrequency;
import com.googlecode.pondskum.timer.SimpleTimer;
import com.googlecode.pondskum.timer.TimerDelay;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public final class ProgressionPanelRunner {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 90;

    private ProgressionPanelRunner() {
        //runner.
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("Progression");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Config config = new DefaultConfigLoader().loadConfig();
        createTimer(frame, config);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createTimer(final JFrame frame, final Config config) {
        TimedAction timedAction = new TimedAction(frame, config);
        SimpleTimer timer = new DefaultTimer(timedAction, getTimerDelayInMilliSeconds(config));
        timedAction.setTimer(timer);
        timer.start();
    }

    private static int getTimerDelayInMilliSeconds(final Config config) {
        RepeatFrequency timerDelay = new TimerDelay(config);
        Logger logger = config.getLogProvider().provide(ProgressionPanelRunner.class);
        logger.info("Using default timer delay of " + timerDelay.getFrequencyInMinutes() + " minutes.");
        return timerDelay.getFrequencyInMilliSeconds();
    }

    private static final class TimedAction implements ActionListener {

        private JFrame parentFrame;
        private LogProvider logProvider;
        private Logger logger;
        private SimpleTimer timer;

        public TimedAction(final JFrame parentFrame, final Config config) {
            this.parentFrame = parentFrame;
            logProvider = config.getLogProvider();
            logger = logProvider.provide(getClass());
        }

        public void setTimer(final SimpleTimer timer) {
            this.timer = timer;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            logger.info("Updating information...");
            BigpondSwingWorker worker = new ProgressionSwingWorker(parentFrame, logProvider);
            worker.addFailureListener(new TimerStopper(timer, logProvider));
            worker.execute();
        }
    }
}
