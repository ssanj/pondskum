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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class RemainderPanel extends JPanel {

    private static final int HUNDRED = 100;
    private double usage;
    private double limit;
    private final DisplayDetailsPack displayDetailsPack;

    public RemainderPanel() {
        usage = 15;
        limit = 25;
        displayDetailsPack = new DefaultDisplayDetailsPack();
    }

    public RemainderPanel(final DisplayDetailsPack displayDetailsPack) {
        this.displayDetailsPack = displayDetailsPack;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        double usageRatio = usage / limit;
        Dimension panelDimension = getSize();
        g.setColor(displayDetailsPack.getBackgroundColour());
        g.fill3DRect(0, 0, panelDimension.width, panelDimension.height, false);

        g.setColor(displayDetailsPack.getUsageColourChooser().getColor(usageRatio));
        g.fill3DRect(0, 0, (int) (panelDimension.width * usageRatio), panelDimension.height, true);

        g.setColor(displayDetailsPack.getLimitTextColour());
        Font oldFont = getFont();
        g.setFont(displayDetailsPack.getQuotaFont());
        String limitText = getUnitValue(limit);
        Dimension limitDimension = LocationFinder.findRightCorner(g, limitText, panelDimension);
        g.drawString(limitText, limitDimension.width, limitDimension.height);

        String usageText = getUnitValue(usage);
        Dimension usageDimension = LocationFinder.findLeftCorner(g, usageText, panelDimension);
        g.drawString(usageText, usageDimension.width, limitDimension.height);
        g.setFont(oldFont);

        g.setColor(displayDetailsPack.getPercentageUsageTextColour());
        String percentageText = ((int) (usageRatio * HUNDRED)) + "%";
        Dimension centreDimension = LocationFinder.findCentreLocation(g, percentageText, panelDimension);
        g.drawString(percentageText, centreDimension.width, centreDimension.height);
    }

    private String getUnitValue(final double value) {
        return value + " " + displayDetailsPack.getQuotaMetrics();
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
