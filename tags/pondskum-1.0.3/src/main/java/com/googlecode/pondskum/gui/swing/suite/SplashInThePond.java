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

import com.googlecode.pondskum.util.DefaultImageLoader;
import com.googlecode.pondskum.util.ImageLoader;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

public final class SplashInThePond extends JWindow {

    private static final long serialVersionUID = 4671640954589173355L;

    public SplashInThePond() {
        ImageLoader imageLoader = new DefaultImageLoader(getClass());
        ImageIcon icon = imageLoader.getImageIcon("splash.jpg");
        setLayout(new BorderLayout());
        add(new JLabel(icon), BorderLayout.CENTER);
        setSize(icon.getIconWidth(), icon.getIconHeight());
        setLocationRelativeTo(null);
    }

    static class SplashDisplayer extends SwingWorker<Void, Void> {

        private SplashInThePond splashInThePond;
        private ActionListener actionListener;

        public SplashDisplayer(final ActionListener actionListener) {
            this.splashInThePond = new SplashInThePond();
            this.actionListener = actionListener;
        }

        @Override
        protected Void doInBackground() throws Exception {
            splashInThePond.setVisible(true);
            Thread.sleep(2000);
            splashInThePond.setVisible(false);
            return null;
        }

        @Override
        protected void done() {
            actionListener.actionPerformed(null);
        }
    }
}
