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

import com.googlecode.pondskum.client.logger.FileWritingConnectionListener;
import com.googlecode.pondskum.client.logger.ConnectionListener;
import com.googlecode.pondskum.client.logger.NullConnectionListener;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.util.Properties;

public final class BigpondConnectorImpl implements BigpondConnector {

    //Come from the properties file.
    private static final String USER_KEY        = "username";
    private static final String PASSWORD_KEY    = "password";
    private static final String TEMP_DIR_KEY    = "tempDir";

    //constant values that don't come from the properties file.
    private static final String TEMP_FILE_NAME  = "usage_data";
    private static final String MY_ACCOUNT      = "myaccount";
    private static final String ON              = "on";

    //Urls used when connecting to bigpond.
    private static final String HOME_URL    = "http://www.bigpond.com/internet/mybigpond/?ref=Net-Head-MyBigPond";
    private static final String LOGIN_URL   = "https://signon.bigpond.com/login";
    private static final String USAGE_URL   = "https://my.bigpond.com/mybigpond/myaccount/myusage/daily/default.do";
    private static final String LOGOUT_URL  = "https://my.bigpond.com/mybigpond/logout.do";
    private static final String GOTO_URL    =
            "https://my.bigpond.com/cdaredirector.do?auth_redir=https://my.bigpond.com/mybigpond/myaccount/default.do/";

    //form element submit from the bigpond login form.
    private static final String USER_FORM_ELEMENT           = "user";
    private static final String USERNAME_FORM_ELEMENT       = "username";
    private static final String PASSWORD_FORM_ELEMENT       = "password";
    private static final String LOGIN_TYPE_FORM_ELEMENT     = "loginType";
    private static final String REMEMBER_ME_FORM_ELEMENT    = "rememberMe";
    private static final String GOTO_FORM_ELEMENT           = "goto";

    private final Properties resourceBundle;

    public BigpondConnectorImpl(final Properties resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public BigpondUsageInformation connect() throws BigpondConnectorException {

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();

            LinkTraverserImpl linkTraverser = new LinkTraverserImpl(httpClient);
            FormSubmitterImpl formSubmitter = new FormSubmitterImpl(httpClient);
            ConnectionListener nullLogger = new NullConnectionListener();

            linkTraverser.traverse(HOME_URL, nullLogger);

            NameValuePairBuilder nameValuePairBuilder = new NameValuePairBuilder();
            nameValuePairBuilder.withName(USER_FORM_ELEMENT).withValue(resourceBundle.getProperty(USER_KEY)).
                            withName(USERNAME_FORM_ELEMENT).withValue(resourceBundle.getProperty(USER_KEY)).
                            withName(PASSWORD_FORM_ELEMENT).withValue(resourceBundle.getProperty(PASSWORD_KEY)).
                            withName(LOGIN_TYPE_FORM_ELEMENT).withValue(MY_ACCOUNT).
                            withName(REMEMBER_ME_FORM_ELEMENT).withValue(ON).
                            withName(GOTO_FORM_ELEMENT).withValue(GOTO_URL);

            formSubmitter.submit(LOGIN_URL, nullLogger, nameValuePairBuilder);
            String tempFileName = createTempFileName();
            linkTraverser.traverse(USAGE_URL, new FileWritingConnectionListener(tempFileName));
            linkTraverser.traverse(LOGOUT_URL, nullLogger);
            httpClient.getConnectionManager().shutdown();

            return new BigpondInformationParser(tempFileName).parse();
        } catch (Exception e) {
            throw new BigpondConnectorException(e);
        }
    }

    private String createTempFileName() {
        return new StringBuilder().append(resourceBundle.getProperty(TEMP_DIR_KEY)).
                                    append(File.separator).
                                    append(TEMP_FILE_NAME).
                                    toString();
    }
}
