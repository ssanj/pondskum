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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.font.LineMetrics;

public final class LocationFinder {

    private static final int PADDING = 10;

    private LocationFinder() {
        //Utility
    }

    public static Dimension findRightCorner(final Graphics g, final String text, final Dimension parentSize) {
        FontedTextDimension textDimension = getTextDimensions(g, text);
        float x = Math.max(0, (parentSize.width - (textDimension.getWidth() + PADDING))); //leave a little space @ the border
        float y = Math.max(0, (parentSize.height + textDimension.getHeight()) / 2 - textDimension.getDescent());
        return new Dimension((int) x, (int) y);
    }

    public static Dimension findLeftCorner(final Graphics g, final String text, final Dimension parentSize) {
        FontedTextDimension textDimension = getTextDimensions(g, text);
        float x = PADDING;
        float y = Math.max(0, (parentSize.height + textDimension.getHeight()) / 2 - textDimension.getDescent());
        return new Dimension((int) x, (int) y);
    }

    public static Dimension findCentreLocation(final Graphics g, final String text, final Dimension parentSize) {
        FontedTextDimension textDimension = getTextDimensions(g, text);
        float x = Math.max(0, (parentSize.width - textDimension.getWidth()) / 2);
        float y = Math.max(0, (parentSize.height + textDimension.getHeight()) / 2 - textDimension.getDescent());
        return new Dimension((int) x, (int) y);
    }

    public static FontedTextDimension getTextDimensions(final Graphics g, final String text) {
        float width = (float) g.getFontMetrics().getStringBounds(text, g).getWidth();
        LineMetrics lm = g.getFontMetrics().getLineMetrics(text, g);
        float ascent = lm.getAscent();
        float descent = lm.getDescent();
        float height = ascent + descent;
        return new FontedTextDimensionBuilder().withHeight(height).withWidth(width).withAscent(ascent).withDescent(descent).build();
    }

}
