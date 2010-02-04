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
package com.googlecode.pondskum.gui.swing.suite;


import com.googlecode.pondskum.gui.swing.tablet.Tablet;
import com.googlecode.pondskum.util.DefaultImageLoader;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import java.awt.Dimension;
import java.awt.TrayIcon;

public final class DefaultGuiFactory implements GuiFactory {

    static final int PROGRESSION_WIDTH = 600;
    static final int PROGRESSION_HEIGHT = 90;

    static final int TABLET_WIDTH = 600;
    static final int TABLET_HEIGHT = 400;

    private JFrame progressionFrame;
    private TrayIcon progressionTrayIcon;
    private Tablet tablet;

    public DefaultGuiFactory() {
        createProgressionFrame();
        createProgressionTrayIcon();
        createTablet();
    }

    private void createProgressionFrame() {
        progressionFrame = new JFrame("Progression");
        progressionFrame.setUndecorated(true);
        progressionFrame.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
        progressionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        progressionFrame.setSize(PROGRESSION_WIDTH, PROGRESSION_HEIGHT);
        progressionFrame.setResizable(false);
        progressionFrame.setLocationRelativeTo(null);
    }

    private void createProgressionTrayIcon() {
        ImageIcon image = new DefaultImageLoader(getClass()).getImageIcon("pondksum.png");
        final TrayIcon trayIcon = new TrayIcon(image.getImage());
        trayIcon.setImageAutoSize(true);
        progressionTrayIcon = trayIcon;
    }

    private void createTablet() {
        tablet = new Tablet();
        tablet.setSize(new Dimension(TABLET_WIDTH, TABLET_HEIGHT));
        tablet.setResizable(true);
        tablet.setLocationRelativeTo(null);
    }

    @Override
    public GUI createProgressionBar() {
        return new ProgressionGui(progressionFrame);
    }

    @Override
    public GUI createProgressionIcon() {
        return new ProgressionTrayGui(progressionTrayIcon);
    }

    @Override
    public GUI createProgressionTablet() {
        return new ProgressionTabletGui(tablet);
    }
}
