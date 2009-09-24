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
package com.googlecode.pondskum.bootstrap;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.googlecode.pinthura.util.SystemPropertyRetriever;
import com.googlecode.pinthura.util.SystemPropertyRetrieverImpl;
import com.googlecode.pondskum.client.BigpondConnector;
import com.googlecode.pondskum.client.BigpondConnectorImpl;
import com.googlecode.pondskum.config.Config;
import com.googlecode.pondskum.config.ConfigFileLoader;
import com.googlecode.pondskum.config.ConfigFileLoaderImpl;
import com.googlecode.pondskum.config.ConfigurationEnum;
import com.googlecode.pondskum.config.DefaultConfig;
import com.googlecode.pondskum.gui.simplecmd.ConnectionPrinter;
import com.googlecode.pondskum.gui.simplecmd.DefaultConnectionPrinter;
import com.googlecode.pondskum.logger.DefaultLogProvider;
import com.googlecode.pondskum.logger.LogProvider;

import java.util.Properties;

public final class PondskumModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SystemPropertyRetriever.class).to(SystemPropertyRetrieverImpl.class).in(Singleton.class);
        bind(ConfigFileLoader.class).to(ConfigFileLoaderImpl.class).in(Singleton.class);
    }

    @Provides @Singleton
    private Config provideConfiguration(final ConfigFileLoader configFileLoader, final LogProvider logProvider) {
        return new DefaultConfig(getConfigurationProperties(configFileLoader), logProvider);
    }

    @Provides @Singleton
    private LogProvider provideLogProvider() {
        return new DefaultLogProvider(ConfigurationEnum.LOG_FILE.getKey());
    }

    @Provides
    private BigpondConnector provideConnector(final Config config) {
        return new BigpondConnectorImpl(config);
    }

    @Provides
    private ConnectionPrinter provideConnectionPrinter(final LogProvider logProvider) {
        return new DefaultConnectionPrinter(logProvider.provide(ConnectionPrinter.class));
    }

    private Properties getConfigurationProperties(final ConfigFileLoader configFileLoader) {
        return configFileLoader.loadProperties(ConfigurationEnum.CONFIG_FILE_LOCATION.getKey());
    }
}
