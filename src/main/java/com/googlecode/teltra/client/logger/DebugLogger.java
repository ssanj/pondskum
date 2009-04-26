package com.googlecode.teltra.client.logger;

import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.cookie.Cookie;

import java.util.Arrays;
import java.util.List;

public final class DebugLogger implements LinkDetailLogger {

    public void log(final DefaultHttpClient httpClient, final HttpResponse response) {
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
}
