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

import org.apache.http.cookie.Cookie;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

public final class Client {

public static void main(String[] args) throws Exception {

        DefaultHttpClient httpclient = new DefaultHttpClient();

        HttpGet httpget = new HttpGet("http://www.bigpond.com/internet/mybigpond/?ref=Net-Head-MyBigPond");

        HttpResponse response = httpclient.execute(httpget);
        System.out.println("bigpond home page: " + response.getStatusLine());
        HttpEntity entity = response.getEntity();
        System.out.println("home page headers->" + Arrays.toString(response.getAllHeaders()));

        if (entity != null) {
            entity.consumeContent();
        }

         List<Cookie> cookies = httpclient.getCookieStore().getCookies();
         if (cookies.isEmpty()) {
             System.out.println("None");
         } else {
             for (int i = 0; i < cookies.size(); i++) {
                 System.out.println("- " + cookies.get(i).toString());
             }
         }


        HttpPost httpost = new HttpPost("https://signon.bigpond.com/login");

         List <NameValuePair> nvps = new ArrayList<NameValuePair>();
         nvps.add(new BasicNameValuePair("user", "sanjivsahayam@bigpond.com"));
         nvps.add(new BasicNameValuePair("username", "sanjivsahayam@bigpond.com"));
         nvps.add(new BasicNameValuePair("password", "b4nj1v"));
         nvps.add(new BasicNameValuePair("loginType", "myaccount"));
         nvps.add(new BasicNameValuePair("rememberMe", "on"));
         nvps.add(new BasicNameValuePair("goto", "https://my.bigpond.com/cdaredirector.do?auth_redir=https://my.bigpond.com/mybigpond/myaccount/default.do/"));

         httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

         response = httpclient.execute(httpost);
         entity = response.getEntity();

         System.out.println("Login form get: " + response.getStatusLine());
        System.out.println("Login form headers->" + Arrays.toString(response.getAllHeaders()));
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
//
        //-----------------

        httpget = new HttpGet("https://my.bigpond.com/mybigpond/myaccount/myusage/daily/default.do");

        response = httpclient.execute(httpget);
        System.out.println("daily usage: " + response.getStatusLine());
        entity = response.getEntity();

        if (entity != null) {
            dumpContent(entity.getContent());
            entity.consumeContent();
        }
        System.out.println("Initial set of cookies:");
        cookies = httpclient.getCookieStore().getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                System.out.println("- " + cookies.get(i).toString());
            }
        }

        httpget = new HttpGet("https://my.bigpond.com/mybigpond/logout.do");
        response = httpclient.execute(httpget);
        System.out.println("logout: " + response.getStatusLine());
        entity = response.getEntity();

        if (entity != null) {
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
