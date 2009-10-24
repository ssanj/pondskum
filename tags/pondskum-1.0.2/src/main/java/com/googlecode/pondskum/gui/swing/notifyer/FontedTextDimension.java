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

public final class FontedTextDimension {

    private float height;
    private float width;
    private float ascent;
    private float descent;

    public void setHeight(final float height) {
        this.height = height;
    }

    public void setWidth(final float width) {
        this.width = width;
    }

    public void setDescent(final float descent) {
        this.descent = descent;
    }

    public void setAscent(final float ascent) {
        this.ascent = ascent;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public float getDescent() {
        return descent;
    }

    public float getAscent() {
        return ascent;
    }
}

