package com.googlecode.teltra.client.logger;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;

public final class NullLogger implements LinkDetailLogger {

    public void log(final DefaultHttpClient httpClient, final HttpResponse response) {
        //do nothing.
    }
}
