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
package com.googlecode.pondskum.util;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.Image;

public final class DefaultImageLoader implements ImageLoader {

    private final Class<?> referenceClass;

    public DefaultImageLoader(final Class<?> referenceClass) {
        this.referenceClass = referenceClass;
    }

    @Override
    public Image getImage(final String url) {
        return getImageIcon(url).getImage();
    }

    @Override
    public ImageIcon getImageIcon(final String url) {
        try {
            return new ImageIcon(referenceClass.getResource(url));
        } catch (Exception e) {
            //TODO: Check for actual type of icon returned.
            return (ImageIcon) UIManager.getIcon("OptionPane.errorIcon");
        }
    }
}
