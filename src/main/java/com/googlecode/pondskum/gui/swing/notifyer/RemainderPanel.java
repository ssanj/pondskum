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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class RemainderPanel extends JPanel {

    private UsageColourChooser usageColourChooser;
    private double usage;

    public RemainderPanel() {
        usageColourChooser = new UsageColourChooser();
        usage = 0;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        Color backgroundColor = Color.GRAY;
        Color limitColor = Color.WHITE;
        Color percentageColor = Color.BLUE;
        double limit = 25;
        double usageRatio = usage / limit;

        Dimension panelDimension = getSize();
        g.setColor(backgroundColor);
        g.fillRect(0, 0, panelDimension.width, panelDimension.height);

        g.setColor(usageColourChooser.getColor(usageRatio));
        g.fillRect(0, 0, (int) (panelDimension.width * usageRatio), panelDimension.height);

        g.setColor(limitColor);
        Font oldFont = getFont();
        g.setFont(oldFont.deriveFont(Font.BOLD));
        Dimension limitDimension = LocationFinder.findRightCorner(g, limit + " GB", panelDimension);
        g.drawString(limit+" GB", limitDimension.width, limitDimension.height);

        Dimension usageDimension = LocationFinder.findLeftCorner(g, usage+ " GB", panelDimension);
        g.drawString(usage+ " GB", usageDimension.width, limitDimension.height);
        g.setFont(oldFont);

        g.setColor(percentageColor);
        String percentageText = ((int) (usageRatio * 100)) + "%";
        Dimension centreDimension = LocationFinder.findCentreLocation(g, percentageText, panelDimension);
        g.drawString(percentageText, centreDimension.width, centreDimension.height);
    }

    public void setUsage() {
        this.usage = (usage + 1) % 25;
        System.out.println("usage = " + usage);
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
        JButton button = new JButton("click");
        parentPanel.add(button, BorderLayout.SOUTH);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                remainderPanel.setUsage();
                remainderPanel.repaint();
            }
        });
        f.setSize(600, 50);
        f.setVisible(true);
    }
}
