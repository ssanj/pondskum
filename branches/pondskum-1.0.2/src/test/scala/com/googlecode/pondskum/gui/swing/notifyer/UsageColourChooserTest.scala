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

package com.googlecode.pondskum.gui.swing.notifyer

import org.junit.Assert.assertThat
import notifyer._
import java.awt.Color
import org.hamcrest.core.IsEqual.equalTo
import org.junit.{Test}
import UsageColourChooserObject._

final class UsageColourChooserTest {

  private val colourChooser = new UsageColourChooser

  @Test def shouldReturnAGreenColourForARangeOfZeroToTwentyFivePercent { assertPercentageRangeWithColour(0, 25, greenColour) }

  @Test def shouldReturnAYellowColourForARangeOfTwentySixToFifty { assertPercentageRangeWithColour(26, 50, yellowColour) }

  @Test def shouldReturnAnOrangeColourForARangeOfFiftyOneToSeventyFive { assertPercentageRangeWithColour(51, 75, orangeColour) }

  @Test def shouldReturnADarkRedColourForARangeOfSeventySixOneToNinety { assertPercentageRangeWithColour(76, 90, darkRedColour) }

  @Test def shouldReturnABrightRedColourForARangeOfNintyOneToHundred { assertPercentageRangeWithColour(91, 100, brightRedColour) }

  private def assertPercentageRangeWithColour(min : Int, max : Int, expectedColor : Color) {
    val randomPercentage = getRandomPercentage(min, max)
    assertThat("failed with percentage of " + randomPercentage, expectedColor, equalTo(colourChooser getColor randomPercentage))
  }
}