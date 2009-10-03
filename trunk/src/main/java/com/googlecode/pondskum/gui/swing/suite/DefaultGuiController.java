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
import com.googlecode.pondskum.gui.swing.notifyer.BigpondSwingWorker;
import com.googlecode.pondskum.gui.swing.notifyer.ConnectionFailureListener;
import com.googlecode.pondskum.gui.swing.notifyer.TimerStopper;
import com.googlecode.pondskum.timer.DefaultTimer;
import com.googlecode.pondskum.timer.RepeatFrequency;
import com.googlecode.pondskum.timer.SimpleTimer;
import com.googlecode.pondskum.timer.TimerDelay;

import javax.swing.JWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

//TODO: Add a way to migrate between guis.
public final class DefaultGuiController implements GuiController {

    private final Config config;
    private final GuiFactory guiFactory;

    private GUI gui;
    private JWindow parentFrame;

    public DefaultGuiController(Config config, final GuiFactory guiFactory) {
        this.config = config;
        this.guiFactory = guiFactory;
    }

    public void run() {
        keepThreadAliveViaWindow();
        startTimer(config);
    }

    //If the thread is not kept alive, its execution completes before the timer is started.
    private void keepThreadAliveViaWindow() {
        parentFrame = new JWindow();
        parentFrame.setVisible(true);
    }

    @Override
    public void notifyStatusChange(final String status) {
        gui.notifyStatusChange(status);
    }

    @Override
    public void connectionSucceeded(final BigpondUsageInformation bigpondUsageInformation) {
        gui.connectionSucceeded(bigpondUsageInformation);
    }

    @Override
    public void connectionFailed(final Exception exception) {
        gui.connectionFailed(exception);
    }

    private void startTimer(final Config config) {
        TimedAction timedAction = new TimedAction();
        RepeatFrequency timerDelay = new TimerDelay(config);
        SimpleTimer timer = new DefaultTimer(timedAction, timerDelay.getFrequencyInMilliSeconds());
        logFrequencyInMinutes(timerDelay, config);
        timedAction.setTimerStopper(new TimerStopper(timer, config.getLogProvider()));
        timer.start();
    }

    private void logFrequencyInMinutes(final RepeatFrequency timerDelay, final Config config) {
        Logger logger = config.getLogProvider().provide(DefaultGuiController.class);
        logger.info("Using default timer delay of " + timerDelay.getFrequencyInMinutes() + " minutes.");
    }


    private final class TimedAction implements ActionListener {

        private Logger logger;
        private ConnectionFailureListener connectionFailureListener;

        public TimedAction() {
            logger = config.getLogProvider().provide(getClass());
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            logger.info("Updating information...");
            BigpondSwingWorker worker = new GuiControllerSwingWorker(DefaultGuiController.this);
            worker.addFailureListener(connectionFailureListener);
            //should there be a better error message if the timer is stopped?
            selectGui();
            worker.execute();
        }

        public void setTimerStopper(final ConnectionFailureListener connectionFailureListener) {
            this.connectionFailureListener = connectionFailureListener;
        }
    }

    //This souldn't happen all the time. Creation should be done once.
    private void selectGui() {
        gui = guiFactory.createProgression();
        gui.display(config);
    }
}
