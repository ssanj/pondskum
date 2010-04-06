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

import com.googlecode.pondskum.config.Config;
import com.googlecode.pondskum.config.DefaultConfigLoader;
import com.googlecode.pondskum.logger.LogProvider;

import java.util.logging.Logger;

public final class ClientBuilder {

    private final Config config;
    private LogProvider logProvider;

    public ClientBuilder() {
        config = new DefaultConfigLoader().loadConfig();
        logProvider = config.getLogProvider();
    }

    public Config getConfig() {
        return config;
    }

    public <T> Logger getLogger(Class<T> targetClass) {
        return logProvider.provide(targetClass);
    }
}
