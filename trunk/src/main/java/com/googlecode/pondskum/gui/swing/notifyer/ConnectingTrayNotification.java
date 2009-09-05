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
import com.googlecode.pondskum.util.ImageLoader;

import java.awt.Image;
import java.util.Arrays;
import java.util.List;

public final class ConnectingTrayNotification implements RollingTrayNotification {

    private final List<Image> images;
    private int index; //defines which image to return next.

    public ConnectingTrayNotification() {
        ImageLoader imageLoader = new DefaultImageLoader(getClass());
        images = Arrays.asList(
                imageLoader.getImage("connecting_1.png"),
                imageLoader.getImage("connecting_2.png"),
                imageLoader.getImage("connecting_3.png")
        );
    }

    public TrayNotification getNotification(final String message) {
        index = (index % images.size());
        return new DefaultTrayNotification(message, images.get(index++));
    }
}
