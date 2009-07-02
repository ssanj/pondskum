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
package com.googlecode.pondskum.gui.swing.tablet;

import java.awt.Dimension;

public final class TabletRunner implements TabletUpateListener {

    public static void main(final String[] args) {
        new TabletRunner().run();
    }

    private void run() {
        Tablet dialog = new Tablet();
        dialog.setUpdateListener(this);
        dialog.setSize(new Dimension(600, 400));
        dialog.setResizable(false);
        executeUpdate(dialog);
        dialog.setVisible(true);
        System.exit(0);
    }

    @Override
    public void updateClicked(final UpdatableTablet tablet) {
        tablet.updateStatus("Reconnecting...");
        executeUpdate(tablet);
    }

    private void executeUpdate(final UpdatableTablet dialog) {
        new TabletSwingWorker(dialog).execute();
    }
}
