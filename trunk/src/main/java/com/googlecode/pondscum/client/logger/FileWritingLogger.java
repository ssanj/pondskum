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
package com.googlecode.teltra.client.logger;

import com.googlecode.pinthura.io.FileTextWriter;
import com.googlecode.pinthura.io.FileTextWriterImpl;
import com.googlecode.pinthura.io.FileWriterFactoryImpl;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public final class FileWritingLogger implements LinkDetailLogger {

    private final String fileName;
    private final FileTextWriter fileTextWriter;

    public FileWritingLogger(final String fileName) {
        this.fileName = fileName;
        fileTextWriter = new FileTextWriterImpl(new FileWriterFactoryImpl());
    }

    public void log(final DefaultHttpClient httpClient, final HttpResponse response) throws LinkDetailLoggerException{
        try {
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                fileTextWriter.write(fileName, dumpContent(entity.getContent()));
                entity.consumeContent();
            }
        } catch (Exception e) {
            throw new LinkDetailLoggerException(e);
        }
    }

    private static List<String> dumpContent(final InputStream incoming) throws IOException {
        InputStream in = new BufferedInputStream(incoming);
        int content;
        StringBuilder sb = new StringBuilder();

        while((content = in.read()) != -1) {
            sb.append((char) content);
        }

        return Arrays.asList(sb.toString());
    }
}
