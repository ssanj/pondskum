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