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
        int content = -1;
        StringBuilder sb = new StringBuilder();

        while((content = in.read()) != -1) {
            sb.append((char) content);
        }

        return Arrays.asList(sb.toString());
    }
}
