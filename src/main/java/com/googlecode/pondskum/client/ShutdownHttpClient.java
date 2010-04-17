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

import com.googlecode.pondskum.config.Config;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.swing.SwingWorker;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ShutdownHttpClient extends SwingWorker<Void, Void> {
    private DefaultHttpClient httpClient;
    private Logger logger;

    public ShutdownHttpClient(final Config config, final DefaultHttpClient httpClient) {
        this.httpClient = httpClient;
        logger = config.getLogProvider().provide(ShutdownHttpClient.class);
    }

    @Override protected Void doInBackground() throws Exception {
        httpClient.getConnectionManager().shutdown();
        logger.log(Level.INFO, "Closing HttpClient.");
        return null;
    }
}
