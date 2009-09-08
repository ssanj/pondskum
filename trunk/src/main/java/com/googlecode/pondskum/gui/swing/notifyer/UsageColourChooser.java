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

    private static final int PERCENTAGE = 100;

    private List<Range> ratiosList;

    public UsageColourChooser() {
        // CHECKSTYLE:OFF
        Range range1 = new Range(0, 25, new Color(0xAD,0xC5,0x13));//#ADC513
        Range range2  = new Range(26, 50, new Color(0xFF, 0xCC, 0x05));//#FFCC05
        Range range3 = new Range(51, 75, new Color(0xFF, 0x5F, 0x05));//#FF5F05
        Range range4 = new Range(76, 90, new Color(0xC5, 0x28, 0x13));//#C52813
        Range range5 = new Range(91, 100, new Color(0xFC, 0x24, 0x06));//#FC2406
        // CHECKSTYLE:ON
        ratiosList = Arrays.asList(range1, range2, range3, range4, range5);
    }

    public Color getColor(double usageRatio) {
        for (Range range : ratiosList) {
            if (range.isInRange((int) (usageRatio * PERCENTAGE))) {
                return range.getColor();
            }
        }

        //this should never happen! :)
        return Color.WHITE;
    }

    private static final class Range {

        private final int min;
        private final int max;
        private final Color color;

        private Range(final int min, final int max, final Color color) {
            this.min = min;
            this.max = max;
            this.color = color;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public Color getColor() {
            return color;
        }

        public boolean isInRange(final int ratio) {
            return ratio >= min && ratio <= max;
        }
    }
}
