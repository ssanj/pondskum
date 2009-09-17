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
import pinthura.util.builder.RandomDataCreatorBuilder

final class UsageColourChooserTest {

  private val colourChooser = createColourChooser
  private val randomDataCreator = createRandomDataChooser

  @Test def shouldReturnGreenForARangeOfZeroToTwentyFivePercent { assertRangeWithColour(0, 26, UsageColourChooserTest.GREEN) }

  @Test def shouldReturnYellowForARangeOfTwentySixToFifty { assertRangeWithColour(26, 51, UsageColourChooserTest.YELLOW) }

  def assertRangeWithColour(minPercent : Int, maxPercentExclusive : Int, expectedColor : Color) {
    val randomPercentage = getPercentage(minPercent, maxPercentExclusive)
    assertThat("failed with percentage of " + randomPercentage, expectedColor, equalTo(colourChooser getColor randomPercentage))
  }

  def createRandomDataChooser = new RandomDataCreatorBuilder build
  def createColourChooser = new UsageColourChooser
  def getPercentage(min : Int, maxExclusive : Int) = randomDataCreator.createBoundedNumber(min, maxExclusive) *
          UsageColourChooserTest.AS_PERCENTAGE

  object UsageColourChooserTest {

    val AS_PERCENTAGE = 0.01
    val GREEN = new Color(0xAD,0xC5,0x13)
    val YELLOW = new Color(0xFF, 0xCC, 0x05)
  }
}