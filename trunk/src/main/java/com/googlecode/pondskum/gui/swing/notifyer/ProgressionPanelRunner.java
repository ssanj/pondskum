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
import com.googlecode.pondskum.config.ConfigFileLoaderImpl;
import com.googlecode.pondskum.util.NumericUtil;
import com.googlecode.pondskum.util.NumericUtilImpl;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public final class ProgressionPanelRunner {

    //TODO: Find a way to reuse constants between ProgressionSwingWorker and this class. Extract a class to handle this maybe?
    //TODO: Move timer-related code into a separate class.
    private static final int MILLI_SECONDS_IN_A_MINUTE  = (60 * 1000);
    private static final int TEN_MINS_IN_MILLI_SECONDS  = 10 * MILLI_SECONDS_IN_A_MINUTE;
    private static final String UPDATE_INTERVAL_KEY     = "update.interval";


    public static void main(final String[] args) {
        JFrame frame = new JFrame("Progression");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createTimer(frame);
        frame.setSize(600, 90);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createTimer(final JFrame frame) {
        final Timer timer = new Timer(0, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println("Performing update...");
                new ProgressionSwingWorker(frame, timer).execute();
            }
        });

        int timerDelay = getTimerDelay();
        timer.setInitialDelay(0);//start immediately.
        timer.setRepeats(true);
        timer.setCoalesce(true); //send only 1 event even if multiple are queued.
        timer.setDelay(timerDelay);
        timer.start();
    }

    private static int getTimerDelay() {
        Properties userProperties = getUserProperties();
        NumericUtil numericUtil = new NumericUtilImpl();
        if (userProperties.containsKey(UPDATE_INTERVAL_KEY)) {
            String interval = userProperties.getProperty(UPDATE_INTERVAL_KEY);
            if (numericUtil.isNumber(interval)) {
                //interval is specified as minutes, so we need to convert it into milliseconds to use it here.
                int intervalInMilliSeconds = getIntervalInMilliSeconds(numericUtil.getNumber(interval));
                //set a minimum of 10 minutes.
                int timerDelay = Math.max(TEN_MINS_IN_MILLI_SECONDS, intervalInMilliSeconds);
                //TODO: Sort out a proper logger.
                System.out.println("Setting timer delay to " + (timerDelay / MILLI_SECONDS_IN_A_MINUTE) +
                        " minutes. (" + timerDelay + " ms)");
                return timerDelay;
            }
        }

        System.out.println("Using default timer delay of 10 minutes.");
        return TEN_MINS_IN_MILLI_SECONDS;
    }

    private static int getIntervalInMilliSeconds(final Integer timerDelay) {
        return timerDelay * MILLI_SECONDS_IN_A_MINUTE;
    }

    private static Properties getUserProperties() {
        return new ConfigFileLoaderImpl(new SystemPropertyRetrieverImpl()).loadProperties(getPropertyFileLocationSystemPropertyKey());
    }

    private static String getPropertyFileLocationSystemPropertyKey() {
        return "bigpond.config.location";
    }

}
