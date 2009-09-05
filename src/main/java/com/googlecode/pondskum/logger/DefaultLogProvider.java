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
package com.googlecode.pondskum.logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default log provider. On creation tries to use a <code>FileHandler</code> as the default handler. What this means is that it tries to
 * log to a file. If it fails to write open the file, it then logs to the console as a fallback.
 */
public final class DefaultLogProvider implements LogProvider {

    private static final int LOG_FILE_COUNT = 2;
    private static final int LOG_FILE_SIZE_IN_BYTES = 2 * 1000 * 1000;

    /**
     * Default constructor that removes all pre-initialized log handlers. We do this so that only the <code>FileHandler</code> we supply
     * is used for logging. By default there are other handles in addition to the ones we provide. (eg. <code>ConsoleHandler</code>).
     * @param logFileName The (path and) name of the log file to write to.
     */
    public DefaultLogProvider(final String logFileName) {
        removeAllHandlers();
        setFileLoggerAsDefaultHandler(logFileName);
    }

    @Override
    public Logger provide(final Class<?> loggerClass, final Level logLevel) {
        Logger logger = Logger.getLogger(loggerClass.getName());
        logger.setLevel(logLevel);
        return logger;
    }

    @Override
    public Logger provide(final Class<?> loggerClass) {
        return provide(loggerClass, Level.INFO);
    }

    private void removeAllHandlers() {
        Logger rootLogger = Logger.getLogger("");
        for (Handler handler : rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
        }
    }

    private void setFileLoggerAsDefaultHandler(final String logFileName) {
        Logger rootLogger = Logger.getLogger("");
        try {
            writeToFile(rootLogger, logFileName);
        } catch (IOException e) {
            writeToConsole(rootLogger, logFileName, e);
        }
    }

    private void writeToFile(final Logger logger, final String logFileName) throws IOException {
        if (logFileName == null || logFileName.trim().isEmpty()) {
            throw new IOException("logFileName not specified.");
        }
        logger.addHandler(new FileHandler(logFileName, LOG_FILE_SIZE_IN_BYTES, LOG_FILE_COUNT, true));
    }

    private void writeToConsole(final Logger logger, final String logFileName, final IOException e) {
        logger.addHandler(new ConsoleHandler());
        logger.log(Level.WARNING, "Could not write to file [" + logFileName + "]. Writing to console. See exception details." , e);
    }
}
