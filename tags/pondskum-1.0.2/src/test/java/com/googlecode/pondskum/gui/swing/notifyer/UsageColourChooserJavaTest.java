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

import com.googlecode.pinthura.util.RandomDataCreator;
import com.googlecode.pinthura.util.builder.RandomDataCreatorBuilder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.awt.Color;

public final class UsageColourChooserJavaTest {

    private static final double AS_PERCENTAGE = 0.01;
    private static final Color GREEN = new Color(0xAD,0xC5,0x13);
    private static final Color YELLOW = new Color(0xFF, 0xCC, 0x05);

    private final UsageColourChooser usageColourChooser = createUsageColourChooser();
    private final RandomDataCreator randomDataCreator = createRandomDataCreator();

    @Test public void shouldReturnGreenForARangeOfZeroToTwentyFivePercent() { assertRangeWithColour(0, 26, GREEN); }

    @Test public void shouldReturnYellowForARangeOfTwentySixToFifty() { assertRangeWithColour(26, 51, YELLOW); }

    @Test public void shouldThrowAnAssertionErrorForAllOtherRanges() {
        double randomPercentage = getRandomPercentage(101, 500);
        try {
            usageColourChooser.getColor(randomPercentage);
        } catch (UnknownUsagePercentageException e) {
            assertThat("unhandled % of " + randomPercentage, equalTo(e.getMessage()));
        }
    }

    private void assertRangeWithColour(final int min, final int maxExclusive, final Color expectedColour) {
        double randomPercentage = getRandomPercentage(min, maxExclusive);
        assertThat("failed with percentage of " + randomPercentage, expectedColour, equalTo(usageColourChooser.getColor(randomPercentage)));
    }

    private double getRandomPercentage(final int min, final int maxExclusive) {
        return randomDataCreator.createBoundedNumber(min, maxExclusive) * AS_PERCENTAGE;
    }

    private RandomDataCreator createRandomDataCreator() { return new RandomDataCreatorBuilder().build(); }

    private UsageColourChooser createUsageColourChooser() { return new UsageColourChooser(); }
}
