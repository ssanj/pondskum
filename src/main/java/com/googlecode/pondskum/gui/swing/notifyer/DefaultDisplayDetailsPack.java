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

    @Override
    public String getQuotaUnits() {
        return "GB";
    }

    @Override
    public Integer getQuotaLimit() {
        return 100;
    }

    @Override
    public Double getTotalUsage() {
        return 75.2d;
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
        return new Color(0xFF, 0x5F, 0x05);
    }
}
