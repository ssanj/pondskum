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
package com.googlecode.pondskum.client.logger;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;

import java.util.List;
import java.util.ArrayList;

public final class CompositeConnectionListener implements ConnectionListener {

    private final List<ConnectionListener> connectionListeners;

    public CompositeConnectionListener(final List<ConnectionListener> connectionListeners) {
        this.connectionListeners = new ArrayList<ConnectionListener>(connectionListeners);
    }

    @Override
    public void onEvent(final DefaultHttpClient httpClient, final HttpResponse response) throws ConnectionListenerException {
        for (ConnectionListener connectionListener : connectionListeners) {
            connectionListener.onEvent(httpClient, response);
        }
    }

    @Override
    public void onError(final String error, final Exception e) {
        for (ConnectionListener connectionListener : connectionListeners) {
            connectionListener.onError(error, e);
        }
    }
}
