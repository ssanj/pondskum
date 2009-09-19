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

import com.googlecode.pinthura.annotation.SuppressionReason;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public final class UsageColourChooser {

    private static final int PERCENT = 100;
    private static final int PERCENTAGE_FOR_RANGE_ONE_START = 0;
    private static final int PERCENTAGE_FOR_RANGE_ONE_END = 25;
    private static final int PERCENTAGE_FOR_RANGE_TWO_START = 26;
    private static final int PERCENTAGE_FOR_RANGE_TWO_END = 50;
    private static final int PERCENTAGE_FOR_RANGE_THREE_START = 51;
    private static final int PERCENTAGE_FOR_RANGE_THREE_END = 75;
    private static final int PERCENTAGE_FOR_RANGE_FOUR_START = 76;
    private static final int PERCENTAGE_FOR_RANGE_FOUR_END = 90;
    private static final int PERCENTAGE_FOR_RANGE_FIVE_START = 91;
    private static final int PERCENTAGE_FOR_RANGE_FIVE_END = 100;

    private List<Range> ratiosList;

    public UsageColourChooser() {

        Range range1 = new Range(PERCENTAGE_FOR_RANGE_ONE_START, PERCENTAGE_FOR_RANGE_ONE_END, createGreenColour());
        Range range2 = new Range(PERCENTAGE_FOR_RANGE_TWO_START, PERCENTAGE_FOR_RANGE_TWO_END, createYellowColour());
        Range range3 = new Range(PERCENTAGE_FOR_RANGE_THREE_START, PERCENTAGE_FOR_RANGE_THREE_END, createOrangeColour());
        Range range4 = new Range(PERCENTAGE_FOR_RANGE_FOUR_START, PERCENTAGE_FOR_RANGE_FOUR_END, createDarkRed());
        Range range5 = new Range(PERCENTAGE_FOR_RANGE_FIVE_START, PERCENTAGE_FOR_RANGE_FIVE_END, createBrightRed());

        ratiosList = Arrays.asList(range1, range2, range3, range4, range5);
    }

    // CHECKSTYLE:OFF
    private Color createBrightRed() {
        return new Color(0xFC, 0x24, 0x06); //#FC2406
    }

    private Color createDarkRed() {
        return new Color(0xC5, 0x28, 0x13); //#C52813
    }

    private Color createOrangeColour() {
        return new Color(0xFF, 0x5F, 0x05); //#FF5F05
    }

    private Color createYellowColour() {
        return new Color(0xFF, 0xCC, 0x05); //#FFCC05
    }

    private Color createGreenColour() {
        return new Color(0xAD,0xC5,0x13); //#ADC513
    }
    // CHECKSTYLE:ON

    @SuppressWarnings({"MethodParameterOfConcreteClass"})
    @SuppressionReason(value = SuppressionReason.Reason.OTHER, desc = "Parameter is a private static final inner class.")
    public Color getColor(double usagePercentage) {
        for (Range range : ratiosList) {
            if (hasColourForPercentageUsage(usagePercentage, range)) {
                return range.getColor();
            }
        }

        throw new UnknownUsagePercentageException("unhandled % of " + usagePercentage);
    }

    @SuppressWarnings({"MethodParameterOfConcreteClass"})
    @SuppressionReason(value = SuppressionReason.Reason.OTHER, desc = "Parameter is a private static final inner class.")
    private boolean hasColourForPercentageUsage(final double usageRatio, final Range range) {
        return range.isInRange((int) (usageRatio * PERCENT));
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

        public Color getColor() {
            return color;
        }

        public boolean isInRange(final int ratio) {
            return ratio >= min && ratio <= max;
        }
    }
}
