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
import com.googlecode.pondskum.timer.RepeatFrequency;
import com.googlecode.pondskum.timer.TimerDelay;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public final class ProgressionPanelRunner {
    //TODO: Find a way to reuse constants between ProgressionSwingWorker and this class. Extract a class to handle this maybe?
    //TODO: Move timer-related code into a separate class.

    private ProgressionPanelRunner() {
        //runner.
    }

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

        timer.setInitialDelay(0);//start immediately.
        timer.setRepeats(true);
        timer.setCoalesce(true); //send only 1 event even if multiple are queued.
        timer.setDelay(getTimerDelayInMilliSeconds());
        timer.start();
    }

    private static int getTimerDelayInMilliSeconds() {
        Properties userProperties = getUserProperties();
        RepeatFrequency timerDelay = new TimerDelay(userProperties);
        //TODO: use logger.
        System.out.println("Using default timer delay of " + timerDelay.getFrequencyInMinutes() + " minutes.");
        return timerDelay.getFrequencyInMilliSeconds();
    }

    private static Properties getUserProperties() {
        return new ConfigFileLoaderImpl(new SystemPropertyRetrieverImpl()).loadProperties(getPropertyFileLocationSystemPropertyKey());
    }

    //TODO: Centralize this value. All code should get the same value unless they want to override it.
    private static String getPropertyFileLocationSystemPropertyKey() {
        return "bigpond.config.location";
    }

}
