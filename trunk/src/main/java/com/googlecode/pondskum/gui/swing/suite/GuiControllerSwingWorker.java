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

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pondskum.gui.swing.notifyer.BigpondSwingWorker;

import java.util.List;

public final class GuiControllerSwingWorker extends BigpondSwingWorker {

    private final GuiController guiController;

    public GuiControllerSwingWorker(final GuiController guiController) {
        this.guiController = guiController;
    }

    @Override
    protected void preConnect() {
        //do nothing.
    }

    @Override
    protected void postConnect() {
        //do nothing.
    }

    @Override
    protected void process(final List<String> statusList) {
        for (String status : statusList) {
            guiController.notifyStatusChange(status);
        }
    }

    @Override
    protected void done() {
        try {
            if (!isCancelled()) {
                showUsage();
                return;
            }

            showError();
        } catch (Exception e) {
            setException(e);
            showError();
        }
    }

    private void showUsage() throws Exception {
        guiController.connectionSucceeded(get());
    }

    @SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
    @SuppressionReason(value = SuppressionReason.Reason.OTHER, desc = "Exception is set for retrieval later.")
    private void showError() {
        guiController.connectionFailed(getException());
    }
}
