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
import com.googlecode.pondskum.timer.DefaultTimer;
import com.googlecode.pondskum.timer.SimpleTimer;
import com.googlecode.pondskum.timer.TimerDelay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class ProgressTrayIconRunner {

    private ProgressTrayIconRunner() {
        //runner.
    }

    public static void main(String[] args) throws ProgressTrayIconInstallationException {
        Config config = new DefaultConfigLoader().loadConfig();
        BigpondTrayIconUsageUpdater usageUpdater = new BigpondTrayIconUsageUpdater();
        installProgressIcon(usageUpdater);
        TrayIconTimedAction trayIconTimedAction = new TrayIconTimedAction(config, usageUpdater);
        SimpleTimer timer = new DefaultTimer(trayIconTimedAction, getRepeatFrequency(config));
        trayIconTimedAction.setTimer(timer); //find out how to get around this.
        timer.start();
    }

    private static int getRepeatFrequency(final Config config) {
        return new TimerDelay(config).getFrequencyInMilliSeconds();
    }

    private static void installProgressIcon(final BigpondTrayIconUsageUpdater usageUpdater)
            throws ProgressTrayIconInstallationException {
        ProgressTrayIcon progressTrayIcon = new ProgressTrayIcon();
        progressTrayIcon.install(usageUpdater);
    }

    private static final class TrayIconTimedAction implements ActionListener {

        private Config config;
        private BigpondTrayIconUsageUpdater usageUpdater;
        private SimpleTimer timer;

        public TrayIconTimedAction(final Config config, final BigpondTrayIconUsageUpdater usageUpdater) {
            this.config = config;
            this.usageUpdater = usageUpdater;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            ProgressTrayIconSwingWorker worker = new ProgressTrayIconSwingWorker(usageUpdater);
            worker.addFailureListener(new TimerStopper(timer, config.getLogProvider()));
            worker.execute();
        }

        //TODO:Clean this up. Pass it in via the constructor.
        public void setTimer(final SimpleTimer timer) {
            this.timer = timer;
        }
    }


}
