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
import com.googlecode.pondskum.gui.swing.notifyer.TimerStopper;
import com.googlecode.pondskum.timer.DefaultTimer;
import com.googlecode.pondskum.timer.RepeatFrequency;
import com.googlecode.pondskum.timer.SimpleTimer;
import com.googlecode.pondskum.timer.TimerDelay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

//TODO: Add a way to migrate between guis.
public final class DefaultGuiController implements GuiController {

    private final Config config;
    private GuiChooser guiChooser;
    private TimerStopper timerStopper;

    public DefaultGuiController(Config config, final GuiChooser guiChooser) {
        this.config = config;
        this.guiChooser = guiChooser;
    }

    public void run() {
        startTimer(config);
    }

    @Override
    public void notifyStatusChange(final String status) {
        guiChooser.getGui().notifyStatusChange(status);
    }

    @Override
    public void connectionSucceeded(final BigpondUsageInformation bigpondUsageInformation) {
        guiChooser.getGui().connectionSucceeded(bigpondUsageInformation);
    }

    @Override
    public void connectionFailed(final Exception exception) {
        guiChooser.getGui().connectionFailed(exception);
    }

    private void startTimer(final Config config) {
        TimedAction timedAction = new TimedAction();
        RepeatFrequency timerDelay = new TimerDelay(config);
        SimpleTimer timer = new DefaultTimer(timedAction, timerDelay.getFrequencyInMilliSeconds());
        logFrequencyInMinutes(timerDelay, config);
        timerStopper = new TimerStopper(timer, config.getLogProvider());
        timer.start();
    }

    private void logFrequencyInMinutes(final RepeatFrequency timerDelay, final Config config) {
        Logger logger = config.getLogProvider().provide(DefaultGuiController.class);
        logger.info("Using default timer delay of " + timerDelay.getFrequencyInMinutes() + " minutes.");
    }

    private final class TimedAction implements ActionListener {

        private Logger logger;

        public TimedAction() {
            logger = config.getLogProvider().provide(getClass());
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            try {
                logger.info("Updating information...");
                BigpondSwingWorker worker = new GuiControllerSwingWorker(DefaultGuiController.this);
                worker.addFailureListener(timerStopper);
                guiChooser.initialiseGui();
                worker.execute();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Timed Action Failed.", e);
                timerStopper.connectionFailed();
            }
        }

    }

}
