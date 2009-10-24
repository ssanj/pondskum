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
package com.googlecode.pondskum.timer;

import javax.swing.Timer;
import java.awt.event.ActionListener;

public final class DefaultTimer implements SimpleTimer {

    private final ActionListener actionListener;
    private int delayInMilliSeconds;
    private final Timer timer;

    public DefaultTimer(final ActionListener actionListener, final int delayInMilliSeconds) {
        this.actionListener = actionListener;
        this.delayInMilliSeconds = delayInMilliSeconds;
        timer = new Timer(0, null);
    }

    @Override
    public void start() {
        timer.addActionListener(actionListener);
        timer.setInitialDelay(0);//start immediately.
        timer.setRepeats(true);
        timer.setCoalesce(true); //send only 1 event even if multiple are queued.
        timer.setDelay(delayInMilliSeconds);
        timer.start();
    }

    @Override
    public void stop() {
        timer.stop();
    }
}
