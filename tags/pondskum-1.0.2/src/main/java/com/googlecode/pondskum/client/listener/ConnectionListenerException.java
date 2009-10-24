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
package com.googlecode.pondskum.client.listener;

public final class ConnectionListenerException extends RuntimeException {

    private static final long serialVersionUID = 3839522759978247399L;

    public ConnectionListenerException(final String message) {
        super(message);
    }

    public ConnectionListenerException(final Throwable cause) {
        super(cause);
    }


    public ConnectionListenerException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
