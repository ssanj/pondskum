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

import com.googlecode.pondskum.client.BigpondUsageInformation;

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
                currentGui = getNewInstanceOfCurrentGui();
                currentGui.setStateChangeListener(DefaultGuiChooser.this);
                currentGui.display();
                return currentGui;
            }
        });
    }

    //TODO: Find a better way to do this.
    private GUI getNewInstanceOfCurrentGui() {
        if (currentGui != null) {
            currentGui.resetForReuse();//this is done directly in each contructor..so it may not be needed.
        }

        if (currentGui instanceof ProgressionGui) {
            return guiFactory.createProgressionBar();
        }

        if (currentGui instanceof ProgressionTrayGui) {
            return guiFactory.createProgressionIcon();
        }

        if (currentGui instanceof ProgressionTabletGui) {
            return guiFactory.createProgressionTablet();
        }

        return guiFactory.createProgressionBar();
    }

    @Override
    public void stateChangeOccured(final GUI gui, final GuiEnumeration nextGui) {
        guiLocker.performThreadsafeGuiUpdate(new Command() {

            @Override
            public GUI execute() throws Exception {
                BigpondUsageInformation usageInfo = currentGui.getUsageInfo();
                String currentStatus = currentGui.getCurrentStatus();
                currentGui.dispose();
                currentGui = chooseNextGui(nextGui);
                currentGui.updateWithCurrentStatus(currentStatus);
                currentGui.updateWithExistingUsage(usageInfo);
                currentGui.setStateChangeListener(DefaultGuiChooser.this);
                currentGui.display();
                return currentGui;
            }
        });
    }

    private GUI chooseNextGui(final GuiEnumeration nextGui) {
        switch(nextGui) {
            case PROGRESSION_BAR: return guiFactory.createProgressionBar();
            case PROGRESS_TRAY: return guiFactory.createProgressionIcon();
            case PROGRESSION_TABLET: return guiFactory.createProgressionTablet();
            default: return guiFactory.createProgressionBar();
        }
    }
}
