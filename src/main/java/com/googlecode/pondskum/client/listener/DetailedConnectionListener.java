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
import com.googlecode.pondskum.logger.LogProvider;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DetailedConnectionListener implements ConnectionListener {

    private final Logger logger;

    public DetailedConnectionListener(final LogProvider logProvider) {
        this.logger = logProvider.provide(getClass());
    }

    public void handleEvent(final DefaultHttpClient httpClient, final HttpResponse response) {
        logger.info("statusline -> " + response.getStatusLine());
        logger.info("Headers -> " + Arrays.toString(response.getAllHeaders()));
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            try {
                dumpCookies(httpClient.getCookieStore().getCookies());
                dumpContent(entity);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Couldn't write content due to an Exception. See below for details", e);
            }
        }
    }

    @Override public void onError(final ConnectionStage stage, final String error, final Exception e) {
        logger.log(Level.SEVERE, error, e);
    }

    @Override public void updateStatus(final ConnectionStage stage, final String statusMessage) {
        logger.info(statusMessage);
    }

    private void dumpContent(final HttpEntity entity) throws IOException {
        InputStream in = new BufferedInputStream(entity.getContent());
        int content;
        StringBuilder sb = new StringBuilder();

        while ((content = in.read()) != -1) {
            sb.append((char) content);
        }

        logger.info("Contents of Request:");
        logger.info(sb.toString());
    }

    private void dumpCookies(final List<Cookie> cookies) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cookies.size(); i++) {
            sb.append(cookies.get(i));
            if (i < (cookies.size() - 1)) {
                sb.append(", ");
            }
        }

        logger.info("Cookies -> " + sb.toString());
    }
}
