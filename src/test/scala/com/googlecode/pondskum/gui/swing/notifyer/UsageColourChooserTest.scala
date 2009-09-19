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

import notifyer._
import org.junit.Assert.assertThat
import java.awt.Color
import org.hamcrest.core.IsEqual.equalTo
import org.junit.{Test}
import org.junit.Assert.fail
import pinthura.util.builder.RandomDataCreatorBuilder

final class UsageColourChooserTest {

  private val colourChooser = createColourChooser
  private val randomDataCreator = createRandomDataCreator

  @Test def shouldReturnGreenForARangeOfZeroToTwentyFivePercent { assertPercentageRangeWithColour(0, 25, greenColour) }

  @Test def shouldReturnYellowForARangeOfTwentySixToFifty { assertPercentageRangeWithColour(26, 50, yellowColour) }

  @Test def shouldReturnOrangeForARangeOfFiftyOneToSeventyFive { assertPercentageRangeWithColour(51, 75, orangeColour) }

  @Test def shouldReturnDarkRedForARangeOfSeventySixOneToNinty { assertPercentageRangeWithColour(76, 90, darkRedColour) }

  @Test def shouldReturnBrightRedForARangeOfNintyOneToHundred { assertPercentageRangeWithColour(91, 100, brightRedColour) }

  @Test
  def shouldThrowAnExceptionForAnyOtherRange {
    val randomPercentage = getRandomPercentage(101, 500)
    try {
      colourChooser getColor randomPercentage
      fail("Expected UnknownUsagePercentageException for % of " + randomPercentage)
    } catch {
      case e : UnknownUsagePercentageException => assertThat("unhandled % of " + randomPercentage, equalTo(e.getMessage))
    }
  }

  private def assertPercentageRangeWithColour(min : Int, max : Int, expectedColor : Color) {
    val randomPercentage = getRandomPercentage(min, max)
    assertThat("failed with percentage of " + randomPercentage, expectedColor, equalTo(colourChooser getColor randomPercentage))
  }

  private def getRandomPercentage(min : Int, max : Int) = randomDataCreator.createBoundedNumber(min, plusOne(max)) *
          UsageColourChooserTest.AS_PERCENTAGE;

  private def greenColour = UsageColourChooserTest.GREEN

  private def yellowColour = UsageColourChooserTest.YELLOW

  private def orangeColour = UsageColourChooserTest.ORANGE

  private def darkRedColour = UsageColourChooserTest.DARK_RED

  private def brightRedColour = UsageColourChooserTest.BRIGHT_RED

  private def plusOne(number : Int) = number + 1

  private def createRandomDataCreator = new RandomDataCreatorBuilder build

  private def createColourChooser = new UsageColourChooser

  private object UsageColourChooserTest {
    val AS_PERCENTAGE = 0.01
    val GREEN = new Color(0xAD,0xC5,0x13)
    val YELLOW = new Color(0xFF, 0xCC, 0x05)
    val ORANGE = new Color(0xFF, 0x5F, 0x05)
    val DARK_RED = new Color(0xC5, 0x28, 0x13)
    val BRIGHT_RED = new Color(0xFC, 0x24, 0x06)
  }
}