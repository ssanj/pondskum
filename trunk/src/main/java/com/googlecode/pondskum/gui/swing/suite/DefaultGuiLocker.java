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

import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DefaultGuiLocker implements GuiLocker {

    private static final long LOCK_TIMEOUT = 2l;
    private final ReentrantLock guiLock;
    private Logger logger;

    public DefaultGuiLocker(final Logger logger) {
        this.logger = logger;
        guiLock = new ReentrantLock();
    }

    public GUI performThreadsafeGuiUpdate(Command command) {
        try {
            guiLock.tryLock(LOCK_TIMEOUT, SECONDS);
            return command.execute();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not perform operation.", e);
            throw new ThreadSafeOperationExecutionException(e);
        } finally {
            guiLock.unlock();
        }
    }
}
