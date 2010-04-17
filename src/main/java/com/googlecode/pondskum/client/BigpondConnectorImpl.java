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

import com.googlecode.pondskum.client.listener.CompositeConnectionListener;
import com.googlecode.pondskum.client.listener.ConnectionListener;
import com.googlecode.pondskum.client.listener.DetailedConnectionListener;
import com.googlecode.pondskum.client.listener.FileWritingConnectionListener;
import com.googlecode.pondskum.client.listener.NullConnectionListener;
import com.googlecode.pondskum.config.Config;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.googlecode.pondskum.client.ConnectionStage.CLOSE_REQUEST_FOR_LOGIN;
import static com.googlecode.pondskum.client.ConnectionStage.CLOSE_REQUEST_FOR_USAGE;
import static com.googlecode.pondskum.client.ConnectionStage.OPEN_REQUEST_FOR_LOGIN;
import static com.googlecode.pondskum.client.ConnectionStage.OPEN_REQUEST_FOR_USAGE;
import static com.googlecode.pondskum.client.ConnectionStage.SUBMIT_REQUEST_FOR_LOGIN;

/**
 * Connects to Bigpond and retrieves all account and usage information. Username, password and various other values are read from the
 * <code>Properties</code> instance supplied.
 */
public final class BigpondConnectorImpl implements BigpondConnector {

    private static final String MY_ACCOUNT                  = "myaccount";
    private static final String ON                          = "on";

    //Urls used when connecting to bigpond.
    private static final String LOGIN_URL = "https://signon.bigpond.com/login";
    private static final String USAGE_URL = "https://my.bigpond.com/mybigpond/myaccount/myusage/daily/default.do";
    private static final String GOTO_URL =
            "https://my.bigpond.com/cdaredirector.do?auth_redir=https://my.bigpond.com/mybigpond/myaccount/default.do/";

    //form element submit from the bigpond login form.
    private static final String USER_FORM_ELEMENT           = "user";
    private static final String USERNAME_FORM_ELEMENT       = "username";
    private static final String PASSWORD_FORM_ELEMENT       = "password";
    private static final String LOGIN_TYPE_FORM_ELEMENT     = "loginType";
    private static final String REMEMBER_ME_FORM_ELEMENT    = "rememberMe";
    private static final String GOTO_FORM_ELEMENT           = "goto";

    private Config config;

    public BigpondConnectorImpl(final Config config) {
        this.config = config;
    }

    /**
     * Connects to Bigpond with the accound information supplied. Takens in a bunch of <code>ConnectionListener</code>s which are
     * notified at each point during the connection/retrieval process.
     * @param userConnectionListeners The list of listeners to notify.
     * @return BigpondUsageInformation is the all the client's account and usage information.
     * @throws BigpondConnectorException If any exception occurs.
     */
    public BigpondUsageInformation connect(final ConnectionListener... userConnectionListeners) throws BigpondConnectorException {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();

            LinkTraverser linkTraverser = new LinkTraverserImpl(httpClient);
            FormSubmitter formSubmitter = new FormSubmitterImpl(httpClient);

            //TODO: Move this into a builder.

            ConnectionListener details = (config.isLoggingRequested()) ?
                    new DetailedConnectionListener(config.getLogProvider()) : new NullConnectionListener();

            //default listener chain.
            ConnectionListener defaultCompositeConnectionListeners = new CompositeConnectionListener(
                    createUserConnectionListeners(details, userConnectionListeners));
            String tempFileName = config.getUsageDataFilePath();

            //chain that writes the usage data.
            ConnectionListener usageWritingListener = createFileWriterWithUserListeners(details, tempFileName,
                    userConnectionListeners);

            login(formSubmitter, defaultCompositeConnectionListeners);
            getUsage(linkTraverser, usageWritingListener);
            closeConnections(httpClient, linkTraverser, defaultCompositeConnectionListeners);

            return new BigpondInformationParser(tempFileName).parse();
        } catch (Exception e) {
            throw new BigpondConnectorException(e);
        }
    }

    private void getUsage(final LinkTraverser linkTraverser, final ConnectionListener usageWritingListener) {
        linkTraverser.traverse(USAGE_URL, createUsageStages(), usageWritingListener);//the usage info is dumped.
    }

    private void login(final FormSubmitter formSubmitter, final ConnectionListener defaultCompositeConnectionListeners) {
        formSubmitter.submit(LOGIN_URL, defaultCompositeConnectionListeners, createUsageStages(), getNameValuePairs());
    }

    private void closeConnections(final DefaultHttpClient httpClient, final LinkTraverser linkTraverser,
                                  final ConnectionListener defaultCompositeConnectionListeners) {
        //shutdown connection.
        new ShutdownConnection(config, linkTraverser, defaultCompositeConnectionListeners).logout();
        //shutdown httpclient in a separate thread.
        new ShutdownHttpClient(config, httpClient).execute();
    }

    private StageHolder createUsageStages() {
        return new StageHolder(OPEN_REQUEST_FOR_USAGE, CLOSE_REQUEST_FOR_USAGE);
    }

    private StageHolder createLoginStages() {
        return new StageHolder(OPEN_REQUEST_FOR_LOGIN, SUBMIT_REQUEST_FOR_LOGIN, CLOSE_REQUEST_FOR_LOGIN);
    }

    /**
     * Adds the <code>details</code> <code>ConnectionLister</code> first before any of the <code>userConnectionListeners</code> and returns
     * the list of updated listeners. The details listener outputs account information to a log file. If logging has been turned on,
     * it may be to discover an error that is occurring. Therefore this listener has to run first before any other listeners are processed.
     * @param details The connectiond details listener.
     * @param userConnectionListeners The listeners supplied by the user.
     * @return A <code>List<ConnectionListener></code> that has the <code>details</code> listener before the user listeners.
     */
    private List<ConnectionListener> createUserConnectionListeners(final ConnectionListener details,
                                                                   final ConnectionListener... userConnectionListeners) {
        List<ConnectionListener> listenerList = new ArrayList<ConnectionListener>();
        listenerList.add(details);
        listenerList.addAll(Arrays.asList(userConnectionListeners));
        return listenerList;
    }

    private NameValuePairBuilder getNameValuePairs() {
        NameValuePairBuilder nameValuePairBuilder = new NameValuePairBuilder();
        nameValuePairBuilder.withName(USER_FORM_ELEMENT).withValue(config.getUsername()).
                withName(USERNAME_FORM_ELEMENT).withValue(config.getUsername()).
                withName(PASSWORD_FORM_ELEMENT).withValue(config.getPassword()).
                withName(LOGIN_TYPE_FORM_ELEMENT).withValue(MY_ACCOUNT).
                withName(REMEMBER_ME_FORM_ELEMENT).withValue(ON).
                withName(GOTO_FORM_ELEMENT).withValue(GOTO_URL);
        return nameValuePairBuilder;
    }

    private ConnectionListener createFileWriterWithUserListeners(final ConnectionListener details, final String tempFileName,
                                                                 final ConnectionListener[] userConnectionListeners) {
        List<ConnectionListener> connectionListenerList = new ArrayList<ConnectionListener>();
        connectionListenerList.add(details);
        connectionListenerList.add(new FileWritingConnectionListener(tempFileName));
        connectionListenerList.addAll(Arrays.asList(userConnectionListeners));
        return new CompositeConnectionListener(connectionListenerList);
    }
}
