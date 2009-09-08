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

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class ProgressPanel extends JPanel {

    private static final long serialVersionUID = 1757547248017510443L;
    private static final int UPLOADS = 1;
    private static final int DOWNLOADS = 10;
    private static final int TOTAL = 25;
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 50;

    private final BackgroundRenderer backgroundRenderer;
    private final AbstractRenderer uploadRenderer;
    private final AbstractRenderer downloadRenderer;
    private final AbstractRenderer remainderRenderer;

    public ProgressPanel(final double uploads, final double downloads, final double total) {
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.black));
        double uploadRatio = uploads / total;
        double downloadRatio = downloads / total;
        double remainderRatio = UPLOADS - (downloadRatio + uploadRatio);
        backgroundRenderer = new BackgroundRenderer();
        uploadRenderer = new UploadRenderer(uploadRatio);
        downloadRenderer = new DownloadRenderer(downloadRatio);
        remainderRenderer = new RemainderRenderer(remainderRatio);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        Dimension size = getSize();
        drawBackground(g, size);
        int uploadLength = drawUploads(g, size);
        int downloadLength = drawDownloads(g, size, uploadLength);
        drawRemainder(g, size, downloadLength + uploadLength);
    }

    private void drawBackground(final Graphics g, final Dimension size) {
        backgroundRenderer.render(g, size);
    }

    private int drawUploads(final Graphics g, final Dimension size) {
        return uploadRenderer.render(g, size, 0);
    }

    private void drawRemainder(final Graphics g, final Dimension size, final int offset) {
        remainderRenderer.render(g, size, offset);
    }

    private int drawDownloads(final Graphics g, final Dimension size, final int uploadLength) {
        return downloadRenderer.render(g, size, uploadLength);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Progression");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.getContentPane().add(new ProgressPanel(UPLOADS, DOWNLOADS, TOTAL), BorderLayout.CENTER);
        f.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        f.setVisible(true);
    }
}
