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

import static com.googlecode.pondskum.gui.swing.suite.ProgressionGui.HEIGHT;
import static com.googlecode.pondskum.gui.swing.suite.ProgressionGui.WIDTH;
import com.googlecode.pondskum.util.DefaultImageLoader;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.TrayIcon;

public final class DefaultGuiFactory implements GuiFactory {

    private JFrame progressionFrame;
    private TrayIcon progressionTrayIcon;

    public DefaultGuiFactory() {
        createProgressionFrame();
        createProgressionTrayIcon();
    }

    private void createProgressionFrame() {
        progressionFrame = new JFrame("Progression");
        progressionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        progressionFrame.setSize(WIDTH, HEIGHT);
        progressionFrame.setLocationRelativeTo(null);
    }

    private void createProgressionTrayIcon() {
        ImageIcon image = new DefaultImageLoader(getClass()).getImageIcon("pondksum.png");
        final TrayIcon trayIcon = new TrayIcon(image.getImage());
        trayIcon.setImageAutoSize(true);
        progressionTrayIcon = trayIcon;
    }

    @Override
    public GUI createProgressionBar() {
        return new ProgressionGui(progressionFrame);
    }

    @Override
    public GUI createProgressionIcon() {
        return new ProgressionTrayGui(progressionTrayIcon);
    }
}