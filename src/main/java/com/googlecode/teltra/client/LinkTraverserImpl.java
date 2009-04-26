package com.googlecode.teltra.client;

import com.googlecode.teltra.client.logger.LinkDetailLogger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public final class LinkTraverserImpl {

    private final DefaultHttpClient httpClient;

    public LinkTraverserImpl(final DefaultHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void traverse(final String url, final LinkDetailLogger logger) throws LinkTraverserException {
        try {
            HttpResponse response = openConnection(httpClient, url);
            logger.log(httpClient, response);
            closeConnection(response);
        } catch (Exception e) {
            throw new LinkTraverserException("There was an error connecting to url -> " + url, e);
        }
    }

    private HttpResponse openConnection(final DefaultHttpClient httpClient, final String url) throws IOException {
        HttpGet httpget = new HttpGet(url);
        return httpClient.execute(httpget);
    }

    private void closeConnection(final HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            entity.consumeContent();
        }
    }
}
