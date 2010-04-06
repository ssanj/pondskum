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

import java.awt.Color;

public final class UploadRenderer extends AbstractRenderer {

    UploadRenderer(final double displayRatio) {
        super(displayRatio);
    }

    @Override
    protected Color getProgressColor() {
        return Color.ORANGE;
    }

    @Override
    protected Color getTextColor() {
        return Color.BLACK;
    }

    @Override
    protected boolean isRaised() {
        return true;
    }

    @Override
    protected String getText() {
        return "Uploads";
    }
}
