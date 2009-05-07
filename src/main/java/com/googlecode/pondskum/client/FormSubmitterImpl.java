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

import com.googlecode.pondskum.client.logger.ConnectionListener;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public final class FormSubmitterImpl implements FormSubmitter {

    private final DefaultHttpClient httpClient;

    public FormSubmitterImpl(final DefaultHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void submit(final String url, final ConnectionListener listener, final NameValuePairBuilder nameValuePairBuilder) {
        try {
            listener.updateStatus("Connecting to url -> " + url);
            HttpPost httpost = openConnection(url, nameValuePairBuilder);
            listener.updateStatus("Submitting form");
            HttpResponse response = httpClient.execute(httpost);
            listener.handleEvent(httpClient, response);
            listener.updateStatus("Closing connecting to url -> " + url);
            closeConnection(response);
        } catch (Exception e) {
            List<NameValuePair> nameValuePairList = nameValuePairBuilder.build();
            String error = "Could not submit form to url -> " + url + ", with properties -> " + nameValuePairList;
            listener.onError(error, e);
            throw new FormSubmitterException(error, e);
        }
    }

    private HttpPost openConnection(final String url, final NameValuePairBuilder nameValuePairBuilder) throws UnsupportedEncodingException {
        HttpPost httpost = new HttpPost(url);
        httpost.setEntity(new UrlEncodedFormEntity(nameValuePairBuilder.build(), HTTP.UTF_8));
        return httpost;
    }

    private void closeConnection(final HttpResponse response) throws IOException {
        final HttpEntity entity = response.getEntity();
        if (entity != null) {
             entity.consumeContent();
        }
    }
}
