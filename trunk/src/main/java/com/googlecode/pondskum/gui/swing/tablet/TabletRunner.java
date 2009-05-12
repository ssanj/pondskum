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

import com.googlecode.pondskum.stub.StubbyBigpondUsageInformationBuilder;

import java.awt.Dimension;

public final class TabletRunner {

    public static void main(String[] args) {
        Tablet dialog = new Tablet();
        dialog.setSize(new Dimension(500,400));
        dialog.setResizable(false);
        //new TabletSwingWorker(dialog).execute();
        dialog.setTabletData(new StubbyBigpondUsageInformationBuilder().build());
        dialog.update("line 1"); //move this to a builder.
        dialog.update("line 2");
        dialog.update("line 3");
        dialog.update("line 4");
        dialog.setVisible(true);
        System.exit(0);
    }
}
