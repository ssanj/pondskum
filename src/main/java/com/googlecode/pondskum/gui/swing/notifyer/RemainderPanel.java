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
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class RemainderPanel extends JPanel {

    private UsageColourChooser usageColourChooser;
    private double usage;
    private double limit;
    private Color backgroundColor;
    private Color limitColor;
    private Color percentageColor;
    private String units;
    private DisplayDetailsPack displayDetailsPack;
    private Font quotaFont;

    public RemainderPanel() {
        usageColourChooser = new UsageColourChooser();
        usage = 15;
        limit = 25;
//        backgroundColor = Color.GRAY;
//        limitColor = Color.WHITE;
//        percentageColor = Color.BLUE;
//        units = "GB";
        displayDetailsPack = new DefaultDisplayDetailsPack();
        backgroundColor = displayDetailsPack.getBackgroundColour();
        limitColor = displayDetailsPack.getLimitTextColour();
        percentageColor = displayDetailsPack.getPercentageUsageTextColour();
        units = displayDetailsPack.getQuotaMetrics();
        quotaFont = displayDetailsPack.getQuotaFont();
    }

    @Override
    protected void paintComponent(final Graphics g) {
        double usageRatio = usage / limit;
        Dimension panelDimension = getSize();
        g.setColor(backgroundColor);
        g.fill3DRect(0, 0, panelDimension.width, panelDimension.height, false);

        g.setColor(usageColourChooser.getColor(usageRatio));
        g.fill3DRect(0, 0, (int) (panelDimension.width * usageRatio), panelDimension.height, true);

        g.setColor(limitColor);
        Font oldFont = getFont();
        g.setFont(quotaFont);
        String limitText = getUnitValue(limit);
        Dimension limitDimension = LocationFinder.findRightCorner(g, limitText, panelDimension);
        g.drawString(limitText, limitDimension.width, limitDimension.height);

        String usageText = getUnitValue(usage);
        Dimension usageDimension = LocationFinder.findLeftCorner(g, usageText, panelDimension);
        g.drawString(usageText, usageDimension.width, limitDimension.height);
        g.setFont(oldFont);

        g.setColor(percentageColor);
        String percentageText = ((int) (usageRatio * 100)) + "%";
        Dimension centreDimension = LocationFinder.findCentreLocation(g, percentageText, panelDimension);
        g.drawString(percentageText, centreDimension.width, centreDimension.height);
    }

    private String getUnitValue(final double value) {
        return value + " " + units;
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Remainder");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        final RemainderPanel remainderPanel = new RemainderPanel();
        JPanel parentPanel = new JPanel(new BorderLayout());
        f.getContentPane().add(parentPanel, BorderLayout.CENTER);
        parentPanel.add(remainderPanel, BorderLayout.CENTER);
        parentPanel.add(new JLabel("Sanjiv Sahayam"), BorderLayout.NORTH);
        f.setSize(600, 50);
        f.setVisible(true);
    }
}
