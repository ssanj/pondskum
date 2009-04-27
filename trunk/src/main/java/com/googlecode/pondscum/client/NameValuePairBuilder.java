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
package com.googlecode.teltra.client;

import com.googlecode.pinthura.annotation.SuppressionReason;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"MethodReturnOfConcreteClass", "InstanceVariableOfConcreteClass", "MethodParameterOfConcreteClass"})
@SuppressionReason(SuppressionReason.Reason.BUILDER_PATTERN)
public final class NameValuePairBuilder {

    private final List<ValueBuilder> valueBuilderList;

    public NameValuePairBuilder() {
        valueBuilderList = new ArrayList<ValueBuilder>();
    }

    public ValueBuilder withName(final String name) {
        return new ValueBuilder(this, name);
    }


    public List<NameValuePair> build() {
        final List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

        for (ValueBuilder valueBuilder : valueBuilderList) {
            nameValuePairList.add(valueBuilder.build());
        }

        return nameValuePairList;
    }

    public static final class ValueBuilder {

        private final NameValuePairBuilder parent;
        private final String name;
        private String value;

        public ValueBuilder(final NameValuePairBuilder parent, final String name) {
            this.parent = parent;
            this.name = name;
        }

        public NameValuePairBuilder withValue(final String value) {
            this.value = value;
            parent.valueBuilderList.add(this);
            return parent;
        }

        private NameValuePair build() {
            return new BasicNameValuePair(name, value);
        }
    }
}
