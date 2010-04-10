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
import org.apache.http.client.HttpClient;

import javax.swing.SwingWorker;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ShutdownConnection extends SwingWorker<Void, Void> {

    private static final String LOGOUT_URL = "https://my.bigpond.com/mybigpond/logout.do";
    private final LinkTraverser linkTraverser;
    private final ConnectionListener connectionListener;
    private final HttpClient httpClient;
    private final Logger logger;

    public ShutdownConnection(final Config config, final LinkTraverser linkTraverser, final ConnectionListener connectionListener,
                              final HttpClient httpClient) {
        this.connectionListener = connectionListener;
        this.httpClient = httpClient;
        this.linkTraverser = linkTraverser;
        logger = config.getLogProvider().provide(ShutdownConnection.class);
    }

    @Override protected Void doInBackground() throws Exception {
        try {
            logger.log(Level.INFO, "Logging out of Bigpond.");
            linkTraverser.traverse(LOGOUT_URL, connectionListener);
            logger.log(Level.INFO, "Closing HttpClient.");
            httpClient.getConnectionManager().shutdown();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Could not shutdown connection properly. See exception for details.", e);
        }
        return null;
    }
}
