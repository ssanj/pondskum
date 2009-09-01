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
package com.googlecode.pondskum.config;

import com.googlecode.pinthura.util.SystemPropertyRetrieverImpl;

public final class DefaultConfigLoader implements ConfigLoader {

    @Override
    public Config loadConfig() {
        return new DefaultConfig(new ConfigFileLoaderImpl(new SystemPropertyRetrieverImpl()).
                loadProperties(getPropertyFileLocationSystemPropertyKey()));
    }

    private String getPropertyFileLocationSystemPropertyKey() {
        return ConfigurationEnum.CONFIG_FILE_LOCATION.getKey();
    }

}
