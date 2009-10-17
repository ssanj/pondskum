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

public final class DefaultGuiChooser implements GuiChooser {

    private final GuiFactory guiFactory;
    private GuiLocker guiLocker;
    private GUI currentGui;

    public DefaultGuiChooser(final GuiFactory guiFactory, final GuiLocker guiLocker) {
        this.guiFactory = guiFactory;
        this.guiLocker = guiLocker;
    }

    @Override
    public GUI getGui() {
       return guiLocker.performThreadsafeGuiUpdate(new Command() {
            @Override
            public GUI execute() throws Exception {
                return currentGui;
            }
        });
    }

    public void initialiseGui() {
        guiLocker.performThreadsafeGuiUpdate(new Command() {
            @Override public GUI execute() throws Exception {
                currentGui = guiFactory.getProgressionBar();//initial gui.
                currentGui.setStateChangeListener(DefaultGuiChooser.this);
                currentGui.display();
                return currentGui;
            }
        });
    }

    @Override
    public void stateChangeOccured(final GUI gui) {
        guiLocker.performThreadsafeGuiUpdate(new Command() {

            @Override
            public GUI execute() throws Exception {
                currentGui.hide();
                currentGui = chooseNextGui(gui);
                currentGui.setStateChangeListener(DefaultGuiChooser.this);
                currentGui.display();
                return currentGui;
            }
        });
    }

    private GUI chooseNextGui(final GUI gui) {
        if (gui instanceof ProgressionGui) {
            return guiFactory.createProgressionIcon();
        } else {
            return guiFactory.getProgressionBar();
        }
    }
}
