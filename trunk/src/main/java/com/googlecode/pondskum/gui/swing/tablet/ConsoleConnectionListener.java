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
package com.googlecode.pondskum.gui.swing.tablet;

import com.googlecode.pondskum.client.logger.ConnectionListener;
import com.googlecode.pondskum.client.logger.ConnectionListenerException;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;

public final class ConsoleConnectionListener implements ConnectionListener {

    private final StatusUpdatable statusUpdatable;

    public ConsoleConnectionListener(final StatusUpdatable statusUpdatable) {
        this.statusUpdatable = statusUpdatable;
    }

    @Override
    public void handleEvent(final DefaultHttpClient httpClient, final HttpResponse response) throws ConnectionListenerException {
        //do nothing.
    }

    @Override
    public void onError(final String error, final Exception e) {
        statusUpdatable.updateStatus(error);
    }

    @Override
    public void updateStatus(final String statusMessage) {
        statusUpdatable.updateStatus(statusMessage);
    }
}
