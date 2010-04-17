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
package com.googlecode.pondskum.client;

import com.googlecode.pondskum.client.listener.ConnectionListener;
import com.googlecode.pondskum.config.Config;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class ShutdownConnection {

    private static final String LOGOUT_URL = "https://my.bigpond.com/mybigpond/logout.do";
    private final LinkTraverser linkTraverser;
    private final ConnectionListener connectionListener;
    private final Logger logger;

    public ShutdownConnection(final Config config, final LinkTraverser linkTraverser, final ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
        this.linkTraverser = linkTraverser;
        logger = config.getLogProvider().provide(ShutdownConnection.class);
    }

    public void logout() {
        logger.log(Level.INFO, "Logging out of Bigpond.");
        linkTraverser.traverse(LOGOUT_URL, createStages(), connectionListener);
    }

    private StageHolder createStages() {
        return new StageHolder(ConnectionStage.OPEN_REQUEST_FOR_LOGOUT, ConnectionStage.CLOSE_REQUEST_FOR_LOGOUT);
    }
}
