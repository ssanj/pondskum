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

import org.apache.http.HttpEntity;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public final class HttpResponseInvocationHandler implements InvocationHandler {

    private static final String NO_CONTENT = "N/A";

    private String content = NO_CONTENT;
    private final HttpEntity httpEntity;

    public HttpResponseInvocationHandler(final HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Exception {
        if ("getContent".equals(method.getName())) {
            if (content.equals(NO_CONTENT)) {
                setContent();
            }

            return new ByteArrayInputStream(content.getBytes());
        }

        //delegate for all other methods.
        return method.invoke(httpEntity, args);
    }

    private void setContent() {
        try {
            InputStream in = new BufferedInputStream(httpEntity.getContent());
            int readContent;
            StringBuilder sb = new StringBuilder();

            while ((readContent = in.read()) != -1) {
                sb.append((char) readContent);
            }

            content = sb.toString();
        } catch (IOException e) {
            //use NO_CONTENT
        }
    }
}
