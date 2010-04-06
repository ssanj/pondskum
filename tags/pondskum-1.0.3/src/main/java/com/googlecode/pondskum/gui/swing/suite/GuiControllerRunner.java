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

import com.googlecode.pondskum.config.Config;
import com.googlecode.pondskum.config.DefaultConfigLoader;

import javax.swing.JWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public final class GuiControllerRunner {

    private GuiControllerRunner() {
        //runner.
    }

    public static void main(String[] args) {
        createApplicationWindow();
        Config config = new DefaultConfigLoader().loadConfig();
        Logger lockerLogger = config.getLogProvider().provide(DefaultGuiLocker.class);
        final DefaultGuiController controller = new DefaultGuiController(config, new DefaultGuiChooser(new DefaultGuiFactory(),
                new DefaultGuiLocker(lockerLogger)));
        ActionListener listener = new ActionListener() { @Override public void actionPerformed(final ActionEvent e) { controller.run(); }};
        new SplashInThePond.SplashDisplayer(listener).execute();
    }

    //If the thread is not kept alive, when its execution completes the application exists..
    private static void createApplicationWindow() {
        JWindow keepAliveWindow = new JWindow();
        keepAliveWindow.setVisible(true);
    }
}
