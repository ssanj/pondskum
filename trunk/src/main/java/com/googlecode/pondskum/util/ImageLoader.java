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
import java.awt.Image;

/**
 * Common interface for loading all <code>Image</code>s and <code>ImageIcon</code>s.
 */
public interface ImageLoader {

    /**
     * Returns an <code>Image</code> given a url for the <code>Image</code>.
     * @param url The image url.
     * @return The <code>Image</code>requested or a default <code>Image</code> if the url was invalid.
     */
    Image getImage(String url);

    /**
     * Returns an <code>ImageIcon</code>given a url for the <code>ImageIcon</code>.
     * @param url The <code>ImageIcon</code> url.
     * @return The <code>ImageIcon</code> requested or a default <code>ImageIcon</code> if the url was invalid.
     */
    ImageIcon getImageIcon(String url);
}
