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

public final class DefaultDisplayDetailsPack extends AbstractDisplayDetailsPack {

    private static final int QUOTA_LIMIT = 100;
    private static final double TOTOAL_USAGE = 75.2d;
    private static final int RED = 0xFF;
    private static final int GREEN = 0x5F;
    private static final int BLUE = 0x05;

    @Override
    public String getQuotaUnits() {
        return "GB";
    }

    @Override
    public Integer getQuotaLimit() {
        return QUOTA_LIMIT;
    }

    @Override
    public Double getTotalUsage() {
        return TOTOAL_USAGE;
    }

    @Override
    public String getAccountName() {
        return "account name";
    }

    @Override
    public String getPlanName() {
        return "account plan";
    }

    @Override
    public Color getErrorTextColour() {
        return new Color(RED, GREEN, BLUE);
    }
}
