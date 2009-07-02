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
package com.googlecode.pondskum.gui.swing.notifyer;

import com.googlecode.pondskum.client.BigpondUsageInformation;
import com.googlecode.pondskum.stub.StubbyBigpondUsageInformationBuilder;

import javax.swing.JFrame;
import javax.swing.SwingWorker;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class ProgressionSwingWorker extends SwingWorker<BigpondUsageInformation, String> {


    @Override
    protected BigpondUsageInformation doInBackground() throws Exception {
//        Properties properties = new ConfigFileLoaderImpl(new SystemPropertyRetrieverImpl()).loadProperties("bigpond.config.location");
//        BigpondConnector bigpondConnector = new BigpondConnectorImpl(properties);
//        return bigpondConnector.connect();
        return new StubbyBigpondUsageInformationBuilder().build();
    }

    @Override
    protected void done() {
        try {
            JFrame f = new JFrame("Progression");
            f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            ProgressionPanel panel = new ProgressionPanel(get());
            f.getContentPane().add(panel.getContentPanel());
            f.setSize(600, 90);
            f.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
