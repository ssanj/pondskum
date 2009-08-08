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

import com.googlecode.pondskum.util.DefaultImageLoader;

import java.awt.Image;

/**
 * Encapsulates the current text and image of a <code>TrayIcon</code> notification.
 */
public abstract class AbstractTrayNotification implements TrayNotification {

    private final String message;
    private final Image image;


    public AbstractTrayNotification(final String message, final String iconPath) {
        this.message = message;
        image = new DefaultImageLoader(getClass()).getImage(iconPath);
    }

    public final String getMessage() {
        return message;
    }

    public final Image getImage() {
        return image;
    }
}
