package com.googlecode.teltra.client.logger;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;

public interface LinkDetailLogger {

    void log(DefaultHttpClient httpClient, HttpResponse response) throws LinkDetailLoggerException;
}
