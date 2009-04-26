package com.googlecode.teltra.client;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

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
