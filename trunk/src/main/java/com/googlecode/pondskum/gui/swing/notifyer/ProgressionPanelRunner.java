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

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class ProgressionPanelRunner {

    private static final int MINUTES = 1000 * 60;

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
                new ProgressionSwingWorker(frame, timer).execute();
            }
        });

        timer.setInitialDelay(0);//start immediately.
        timer.setRepeats(true);
        timer.setCoalesce(true); //send only 1 event even if multiple are queued.
        timer.setDelay(MINUTES * 60);//then every hour by default.
        timer.start();
    }
}
