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

import javax.swing.JFrame;

public final class DefaultGuiFactory implements GuiFactory {

    private JFrame progressionFrame;

    public DefaultGuiFactory() {
        createProgressionFrame();
    }

    private void createProgressionFrame() {
        progressionFrame = new JFrame("Progression");
        progressionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        progressionFrame.setSize(WIDTH, HEIGHT);
        progressionFrame.setLocationRelativeTo(null);
    }

    @Override
    public GUI getProgression() {
        return new ProgressionGui(progressionFrame);
    }

    @Override
    public GUI createTrayIcon() {
        return null;
    }
}
