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
import java.util.Arrays;
import java.util.List;

public final class UsageColourChooser {

    private List<Color> ratiosList;

    public UsageColourChooser() {
        Color zeroPercent = new Color(0xAD,0xC5,0x13);//#ADC513
        Color twentyFivePercent = new Color(0xFF, 0xCC, 0x05);//#FFCC05
        Color fiftyPercent = new Color(0xFF, 0x5F, 0x05);//#FF5F05
        Color seventyFivePercent = new Color(0xFF, 0x41, 0x05);//#FF4105
        Color ninetyPercent = new Color(0xC5, 0x28, 0x13);//#C52813
        Color hundredPercent = new Color(0xFC, 0x24, 0x06);//#FC2406
        ratiosList = Arrays.asList(zeroPercent, twentyFivePercent, fiftyPercent, seventyFivePercent, ninetyPercent, hundredPercent);
    }

    public Color getColor(double usageRatio) {
        int index = (int) (usageRatio * (ratiosList.size() - 1));
        return ratiosList.get(index);
    }
}
