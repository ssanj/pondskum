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
package com.googlecode.pondskum.gui.swing.tablet;

import com.googlecode.pondskum.config.Config;
import com.googlecode.pondskum.config.DefaultConfigLoader;
import com.googlecode.pondskum.gui.swing.notifyer.TimerStopper;
import com.googlecode.pondskum.timer.DefaultTimer;
import com.googlecode.pondskum.timer.RepeatFrequency;
import com.googlecode.pondskum.timer.SimpleTimer;
import com.googlecode.pondskum.timer.TimerDelay;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class TabletRunner {

    public static void main(final String[] args) {
        new TabletRunner().run();
    }

    private void run() {
        Tablet dialog = new Tablet();
        dialog.setSize(new Dimension(600, 400));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        createTimer(dialog);
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createTimer(final UpdatableTablet dialog) {
        Config config = new DefaultConfigLoader().loadConfig();
        TableTimedAction timedAction = new TableTimedAction(dialog, config);
        RepeatFrequency repeatFrequency = new TimerDelay(config);
        SimpleTimer timer = new DefaultTimer(timedAction, 60000);/*repeatFrequency.getFrequencyInMilliSeconds());*/
        timedAction.setTimer(timer);
        timer.start();
    }

    private static final class TableTimedAction implements ActionListener {

        private final UpdatableTablet parentDialog;
        private SimpleTimer timer;
        private final Config config;

        public TableTimedAction(final UpdatableTablet parentDialog, final Config config) {
            this.parentDialog = parentDialog;
            this.config = config;
        }

        public void setTimer(final SimpleTimer timer) {
            this.timer = timer;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            TabletSwingWorker worker = new TabletSwingWorker(parentDialog);
            worker.addFailureListener(new TimerStopper(timer, config.getLogProvider()));
            worker.execute();
        }
    }
}
