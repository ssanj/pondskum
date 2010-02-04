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

import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class ContextMenuActions {

    public static ActionListener createBarTransition(final GUI activeGui, final GUI.StateChangeListener stateChangeListener) {
        return new ShowBarAction(activeGui, stateChangeListener);
    }

    public static ActionListener createTabletTransition(final GUI activeGui, final GUI.StateChangeListener stateChangeListener) {
        return new ShowTabletAction(activeGui, stateChangeListener);
    }

    public static ActionListener createMinimizeToTrayTransition(final GUI activeGui, final GUI.StateChangeListener stateChangeListener) {
        return new MinimizeToTrayAction(activeGui, stateChangeListener);
    }

    public static ActionListener createExitTransition() {
        return new ExitSystemAction();
    }

    private static void performLater(final GUI activeGui, final GUI.StateChangeListener stateChangeListener, final GuiEnumeration nextGui) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                stateChangeListener.stateChangeOccured(activeGui, nextGui);
            }
        });
    }

    private static final class ShowBarAction implements ActionListener {

        private final GUI activeGui;
        private final GUI.StateChangeListener stateChangeListener;

        public ShowBarAction(final GUI activeGui, final GUI.StateChangeListener stateChangeListener) {
            this.activeGui = activeGui;
            this.stateChangeListener = stateChangeListener;
        }

        @Override public void actionPerformed(final ActionEvent e) {
            performLater(activeGui, stateChangeListener, GuiEnumeration.PROGRESSION_BAR);
        }
    }

    private static class ShowTabletAction implements ActionListener {

        private final GUI activeGui;
        private final GUI.StateChangeListener stateChangeListener;

        public ShowTabletAction(final GUI activeGui, final GUI.StateChangeListener stateChangeListener) {
            this.activeGui = activeGui;
            this.stateChangeListener = stateChangeListener;
        }

        @Override public void actionPerformed(final ActionEvent e) {
            performLater(activeGui, stateChangeListener, GuiEnumeration.PROGRESSION_TABLET);
        }
    }

    private static class MinimizeToTrayAction implements ActionListener {

        private final GUI activeGui;
        private final GUI.StateChangeListener stateChangeListener;

        public MinimizeToTrayAction(final GUI activeGui, final GUI.StateChangeListener stateChangeListener) {
            this.activeGui = activeGui;
            this.stateChangeListener = stateChangeListener;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            performLater(activeGui, stateChangeListener, GuiEnumeration.PROGRESS_TRAY);
        }
    }

    private static class ExitSystemAction implements ActionListener {
        @Override public void actionPerformed(final ActionEvent e) {
            System.exit(0);
        }
    }
}
