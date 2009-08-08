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
package com.googlecode.pondskum.gui.swing.notifyer;

import com.googlecode.pinthura.util.SystemPropertyRetrieverImpl;
import com.googlecode.pondskum.client.BigpondConnector;
import com.googlecode.pondskum.client.BigpondConnectorImpl;
import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.client.listener.DetailedConnectionListener;
import com.googlecode.pondskum.config.ConfigFileLoaderImpl;
import com.googlecode.pondskum.gui.swing.tablet.ConsoleConnectionListener;
import com.googlecode.pondskum.gui.swing.tablet.StatusUpdatable;

import javax.swing.SwingWorker;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Base class for all worker classes that need to connect to Bigpond. Essentially all Swing guis will go through the same steps. By
 * creating a template here, we centralize the common code and override what we need in the subclasses.
 *
 * A subclass would typically have to override the following methods:
 *
 *  1. postConnect  (from this class)
 *     This is called following a successful connection.
 *  2. process(final List<String> statusList) (from SwingWorker)
 *     This is called when updates are received.
 *  3. done (from SwingWorker)
 *     This is called once the worker completes either successfully or unsuccessfully.
 *
 * @see javax.swing.SwingWorker
 */
public abstract class BigpondSwingWorker extends SwingWorker<BigpondUsageInformation, String> implements StatusUpdatable {

    private Properties properties;
    private BigpondUsageInformation usageInformation;
    private Exception exception;
    private Logger logger;

    protected BigpondSwingWorker() {
        // use the same logging strategy as DetailedConnectionListener.
        logger = Logger.getLogger(DetailedConnectionListener.class.getPackage().getName());
    }

    /**
     * Override this method if you need to do any post-connection processing. At this point both {@link #getProperties()} and
     * {@link #getUsageInformation()} are usable. This method will not be called if there is a connection exception.
     */
    protected abstract void postConnect();

    /**
     * @see javax.swing.SwingWorker#process(java.util.List)
     */
    @Override
    protected abstract void process(List<String> statusList);


    /**
     * @see javax.swing.SwingWorker#done()
     */
    @Override
    protected abstract void done();

    /**
     * The template of any <code>BigpondSwingWorker</code>. A connection is first made followed by a call to {@link #postConnect()}. The
     * retrieved <code>BigpondUsageInformation</code> is then returned. If an exception occurs during the connection, the exception is set
     * (and thus retrievable by {@link #getException()}, the worker is cancelled and a null value is returned.
     * @return <code>BigpondUsageInformation</code> if the connection succeeds or null if it does not.
     * @throws Exception If an <code>java.lang.Error</code> occurs.
     */
    @Override
    protected final BigpondUsageInformation doInBackground() throws Exception {
        try {
            connect();
            postConnect();
            return usageInformation;
        } catch (Exception e) {
            exception = e;
            cancel(true);
            return null;
        }
    }

    /**
     * Method on the <code>StatusUpdatable</code> interface. This is called whenever there is a connection update.
     * @param update The update.
     */
    @Override
    public final void updateStatus(final String update) {
        publish(update); //this indirectly pushes updates to the {@link SwingWorker#process} method.
    }

    /**
     * This method should only be called after a call to {@link #loadProperties()} or {@link #connect()}
     * @return The properties used to connect to Bigpond.
     */
    protected final Properties getProperties() {
        return properties;
    }

    /**
     * This method should only be called after a call to {@link #connect()}
     * @return The <code>BigpondUsageInformation</code> retrieved from Bigpond.
     */
    protected final BigpondUsageInformation getUsageInformation() {
        return usageInformation;
    }


    /**
     * Returns the nested <code>Exception</code>s that occurred during the connection. Each level of exception is nested within the
     * previous.
     * @return <code>Exception</code> that occurred during the connection.
     */
    protected final Exception getException() {
        return exception;
    }

    /**
     * Returns the workers logger.
     * @return The logger in use.
     */
    protected final Logger getLogger() {
        return logger;
    }

    /**
     * Sets the <code>Exception</code> for this connection.
     * @param exception The <code>Exception</code> that occurred during the connection.
     */
    protected final void setException(final Exception exception) {
        this.exception = exception;
    }

    /**
     * Connects to Bigpond with the properties supplied. After call this method both the properties used and the
     * <code>BigpondUsageInformation</code> returned can be retrieved via calls to {@link #getProperties()} and
     * {@link #getUsageInformation()} respectively.
     */
    protected final void connect() {
        loadProperties();
        BigpondConnector bigpondConnector = new BigpondConnectorImpl(properties);
        usageInformation = bigpondConnector.connect(new ConsoleConnectionListener(this));
    }

    /**
     * Returns the message of the first <code>Exception</code> that was thrown, in the case of nested exceptions. If only one
     * <code>Exception</code> was thrown, returns that <code>Exception</code>'s message.
     * @return The message of the first exception that was thrown.
     */
    protected final String getSimpleExceptionMessage() {
        Throwable nested = getException();
        while (nested.getCause() != null) {
            nested = nested.getCause();
        }

        return nested.getMessage();
    }

    /**
     * Override this in subclasses to when defining a non-standard system property key.
     * @return The system property key that holds the location of the property file to use when connecting to Bigpond.
     */
    protected String getPropertyFileLocationSystemPropertyKey() {
        return "bigpond.config.location";
    }

    /**
     * Loads the properties file whose location is specified by the system property key returned from the
     * {@link #getPropertyFileLocationSystemPropertyKey()} method.
     */
    private void loadProperties() {
        properties = new ConfigFileLoaderImpl(new SystemPropertyRetrieverImpl()).loadProperties(getPropertyFileLocationSystemPropertyKey());
    }
}
