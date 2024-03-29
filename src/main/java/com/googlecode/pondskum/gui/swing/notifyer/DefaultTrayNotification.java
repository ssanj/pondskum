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
package com.googlecode.pondskum.gui.swing.notifyer;

import java.awt.Image;

public final class DefaultTrayNotification implements TrayNotification {

    private final String message;
    private final Image image;

    public DefaultTrayNotification(final String message, final Image image) {
        this.message = message;
        this.image = image;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Image getImage() {
        return image;
    }
}
