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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.Arrays;
import java.util.List;

public final class DebuggingConnectionListener implements ConnectionListener {

    public void handleEvent(final DefaultHttpClient httpClient, final HttpResponse response) {
        System.out.println("statusline-> " + response.getStatusLine());
        System.out.println("headers-> " + Arrays.toString(response.getAllHeaders()));
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            List<Cookie> cookies = httpClient.getCookieStore().getCookies();
            System.out.println("Cookies-> ");

            for (int i = 0; i < cookies.size(); i++) {
                System.out.println(cookies.get(i).toString());
                if (i < (cookies.size() - 1)) {
                    System.out.println(", ");
                }
            }
        }
    }

    @Override
    public void onError(final String error, final Exception e) {
        //do nothing.
    }

    @Override
    public void updateStatus(final String statusMessage) {
        //do nothing.
    }
}
