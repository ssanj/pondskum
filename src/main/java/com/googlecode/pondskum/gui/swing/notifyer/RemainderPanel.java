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

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.LineMetrics;

public final class RemainderPanel extends JPanel {

    @Override
    protected void paintComponent(final Graphics g) {
        Color backgroundColor = Color.GRAY;
        Color limitColor = Color.WHITE;
        Color percentageColor = Color.BLUE;
        double usageRatio = 0.05;

        Dimension panelDimension = getSize();
        g.setColor(backgroundColor);
        g.fillRect(0, 0, panelDimension.width, panelDimension.height);

        g.setColor(getUsageColor(usageRatio));
        g.fillRect(0, 0, (int) (panelDimension.width * usageRatio), panelDimension.height);

        g.setColor(limitColor);
        Font oldFont = getFont();
        g.setFont(oldFont.deriveFont(Font.BOLD));
        Dimension limitDimension = getLimitLocation(g, "25 GB", panelDimension);
        g.drawString("25 GB", limitDimension.width, limitDimension.height);
        g.setFont(oldFont);

        g.setColor(percentageColor);
        String percentageText = ((int) (usageRatio * 100)) + "%";
        Dimension centreDimension = getCentreLocation(g, percentageText, panelDimension);
        g.drawString(percentageText, centreDimension.width, centreDimension.height);
    }

    private Color getUsageColor(final double usageRatio) {
        if (usageRatio > 0.5) {
            return Color.GREEN;
        }

        if (usageRatio > 0.25) {
            return Color.ORANGE;
        }

        return Color.RED;
    }

    private Dimension getLimitLocation(final Graphics g, final String text, final Dimension parentSize) {
        StringDimensions stringDimensions = getStringHeightAndWidth(g, text, parentSize);
        float x = Math.max(0, (parentSize.width - (stringDimensions.getWidth() + 10))); //leave a little space @ the border
        float y = Math.max(0, (parentSize.height + stringDimensions.getHeight()) / 2 - stringDimensions.getDescent());
        return new Dimension((int) x, (int) y);
    }

    private Dimension getCentreLocation(final Graphics g, final String text, final Dimension parentSize) {
        StringDimensions stringDimensions = getStringHeightAndWidth(g, text, parentSize);
        float x = Math.max(0, (parentSize.width - stringDimensions.getWidth()) / 2);
        float y = Math.max(0, (parentSize.height + stringDimensions.getHeight()) / 2 - stringDimensions.getDescent());
        return new Dimension((int) x, (int) y);
    }

    private StringDimensions getStringHeightAndWidth(final Graphics g, final String text, final Dimension parentSize) {
        float width = (float) g.getFontMetrics().getStringBounds(text, g).getWidth();
        LineMetrics lm = g.getFontMetrics().getLineMetrics(text, g);
        float ascent = lm.getAscent();
        float descent = lm.getDescent();
        float height = ascent + descent;
        return new StringDimensions(height, width, ascent, descent);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Remainder");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.getContentPane().add(new RemainderPanel(), BorderLayout.CENTER);
        f.setSize(600, 50);
        f.setVisible(true);
    }

    private final class StringDimensions {

        private float height;
        private float width;
        private final float ascent;
        private float descent;

        private StringDimensions(final float height, final float width, final float ascent, final float descent) {
            this.height = height;
            this.width = width;
            this.ascent = ascent;
            this.descent = descent;
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
}
