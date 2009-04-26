package com.googlecode.teltra.client;

import com.googlecode.teltra.client.logger.LinkDetailLogger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public final class FormSubmitterImpl {

    private final DefaultHttpClient httpClient;

    public FormSubmitterImpl(final DefaultHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void submit(final String url, final LinkDetailLogger logger, final NameValuePairBuilder nameValuePairBuilder) {
        try {
            HttpPost httpost = openConnection(url, nameValuePairBuilder);
            HttpResponse response = httpClient.execute(httpost);
            logger.log(httpClient, response);
            closeConnection(response);
        } catch (Exception e) {
            throw new FormSubmitterException("Could not submit form to url -> " + url + ", with properties -> " +
                    nameValuePairBuilder.build(), e);
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
