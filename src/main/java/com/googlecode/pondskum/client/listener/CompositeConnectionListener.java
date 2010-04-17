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
package com.googlecode.pondskum.client.listener;

import com.googlecode.pondskum.client.ConnectionStage;
import com.googlecode.pondskum.client.HttpResponseInvocationHandler;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public final class CompositeConnectionListener implements ConnectionListener {

    private final List<ConnectionListener> connectionListeners;

    public CompositeConnectionListener(final List<ConnectionListener> connectionListeners) {
        this.connectionListeners = new ArrayList<ConnectionListener>(connectionListeners);
    }

    @Override
    public void handleEvent(final DefaultHttpClient httpClient, final HttpResponse response) throws ConnectionListenerException {
        for (ConnectionListener connectionListener : connectionListeners) {

            //proxy the entity content so it can be queried more than once.
            if (response.getEntity() != null) {
                HttpEntity proxiedEntity = (HttpEntity) Proxy.newProxyInstance(getClass().getClassLoader(),
                                            new Class<?>[]{HttpEntity.class},
                                            new HttpResponseInvocationHandler(response.getEntity()));
                response.setEntity(proxiedEntity);
            }

            connectionListener.handleEvent(httpClient, response);
        }
    }

    @Override public void onError(final ConnectionStage stage, final String error, final Exception e) {
        for (ConnectionListener connectionListener : connectionListeners) {
            connectionListener.onError(stage, error, e);
        }
    }

    @Override public void updateStatus(final ConnectionStage stage, final String statusMessage) {
        for (ConnectionListener connectionListener : connectionListeners) {
            connectionListener.updateStatus(stage, statusMessage);
        }
    }
}
