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

import com.googlecode.pondskum.logger.LogProvider;
import com.googlecode.pondskum.timer.SimpleTimer;

import java.util.logging.Logger;

public final class TimerStopper implements ConnectionFailureListener {

    private final SimpleTimer simpleTimer;
    private Logger logger;

    public TimerStopper(final SimpleTimer simpleTimer, final LogProvider logProvider) {
        this.simpleTimer = simpleTimer;
        logger = logProvider.provide(getClass());
    }

    @Override
    public void connectionFailed() {
        logger.info("Timer stopping...");
        simpleTimer.stop();
        logger.info("Timer stopped.");
    }
}
