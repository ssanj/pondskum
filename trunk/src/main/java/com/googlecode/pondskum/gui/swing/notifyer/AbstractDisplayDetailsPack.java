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
import java.awt.Font;

public abstract class AbstractDisplayDetailsPack implements DisplayDetailsPack {

    @Override
    public Color getBackgroundColour() {
        return Color.GRAY;
    }

    @Override
    public Color getLimitTextColour() {
        return Color.WHITE;
    }

    @Override
    public Color getPercentageUsageTextColour() {
        return Color.BLUE;
    }

    @Override
    public Color getErrorTextColour() {
        return Color.RED;
    }

    @Override
    public Font getQuotaFont() {
        return new Font("Dialog", Font.BOLD, 12);
    }

    @Override
    public Font getErrorLabelFont() {
        return getQuotaFont();
    }

    @Override
    public Font getErrorMessageFont() {
        return getQuotaFont().deriveFont(Font.ITALIC, 12);
    }

    @Override
    public UsageColourChooser getUsageColourChooser() {
        return new UsageColourChooser();
    }

    @Override
    public String getQuotaLimitWithUnits() {
        return getQuotaLimit() + " " + getQuotaUnits();
    }

}
