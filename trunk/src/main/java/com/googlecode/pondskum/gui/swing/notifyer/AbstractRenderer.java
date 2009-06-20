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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.font.LineMetrics;

public abstract class AbstractRenderer {

    private double displayRatio;

    protected AbstractRenderer(final double displayRatio) {
        this.displayRatio = displayRatio;
    }

    public final int render(final Graphics g, final Dimension size, final int offset) {
        int displayLength = (int) Math.max(0, (size.width * displayRatio));
        if (displayLength != 0) {
            g.setColor(getProgressColor());
            g.fill3DRect(offset, 0, displayLength, size.height, isRaised());
            g.setColor(getTextColor());
            Dimension centreSize = findCentre(g, getText(), new Dimension(displayLength, size.height));
            drawString(g, offset, centreSize, getText());
        }

        return displayLength;
    }

    protected double getDisplayRatio() {
        return displayRatio;
    }

    protected void drawString(final Graphics g, final int startIndex, final Dimension centreSize, final String text) {
        g.drawString(text, startIndex + centreSize.width, centreSize.height);
    }

    protected Dimension findCentre(final Graphics g, final String text, final Dimension size) {
         float width = (float) g.getFontMetrics().getStringBounds(text, g).getWidth();
         LineMetrics lm = g.getFontMetrics().getLineMetrics(text, g);
         float ascent = lm.getAscent();
         float descent = lm.getDescent();
         float height = ascent + descent;               // without the leading
         float x = Math.max(0, (size.width - width) / 2);                 // center text
         float y = Math.max(0, (size.height + height) / 2 - descent);      //    in label
         return new Dimension((int) x, (int) y);
    }

    protected abstract Color getProgressColor();
    protected abstract Color getTextColor();
    protected abstract boolean isRaised();
    protected abstract String getText();

}
