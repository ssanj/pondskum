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
package testing;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Client2 {

    public static void main(String[] args) throws Exception {

        DefaultHttpClient httpclient = new DefaultHttpClient();



        HttpGet httpget = new HttpGet("https://portal.sun.com/cwplogin/login.jsp?liferayUrl=https://portal.sun.com&cwpIdmUrl=https://identity.sun.com");

        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        System.out.println("Headers1->" + Arrays.asList(response.getAllHeaders()));

        System.out.println("Login form get: " + response.getStatusLine());
        if (entity != null) {
        //    dumpContent(entity.getContent());
            entity.consumeContent();
        }
        System.out.println("Initial set of cookies:");
        List<Cookie> cookies = httpclient.getCookieStore().getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                System.out.println("- " + cookies.get(i).toString());
            }
        }

        HttpPost httpost = new HttpPost("https://identity.sun.com/amserver/UI/Login?org=self_registered_users&goto=https://portal.sun.com");

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("IDToken1", "sanjsmailbox@gmail.com"));
        nvps.add(new BasicNameValuePair("IDToken2", "jeev2000"));

        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

        response = httpclient.execute(httpost);
        entity = response.getEntity();
        System.out.println("Headers2->" + Arrays.asList(response.getAllHeaders()));

        System.out.println("Login form get: " + response.getStatusLine());
        if (entity != null) {
            dumpContent(entity.getContent());
            entity.consumeContent();
        }

        System.out.println("Post logon cookies:");
        cookies = httpclient.getCookieStore().getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                System.out.println("- " + cookies.get(i).toString());
            }
        }

        httpget = new HttpGet("https://portal.sun.com/c");
        response = httpclient.execute(httpget);
        entity = response.getEntity();
        System.out.println("Headers3->" + Arrays.asList(response.getAllHeaders()));
        System.out.println("redirect get: " + response.getStatusLine());
        if (entity != null) {
            dumpContent(entity.getContent());
            entity.consumeContent();
        }


        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();
    }

    private static void dumpContent(final InputStream incoming) throws IOException {
        InputStream in = new BufferedInputStream(incoming);
        int content = -1;
        StringBuilder sb = new StringBuilder();

        while((content = in.read()) != -1) {
            sb.append((char) content);
        }

        System.out.println("content->" + sb.toString());
    }

}
