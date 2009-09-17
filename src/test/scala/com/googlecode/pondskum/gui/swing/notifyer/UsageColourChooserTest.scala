package com.googlecode.pondskum.gui.swing.notifyer


import notifyer._
import org.junit.Assert.assertThat
import java.awt.Color
import org.hamcrest.core.IsEqual.equalTo
import org.junit.{Test}
import pinthura.util.builder.RandomDataCreatorBuilder

final class UsageColourChooserTest {

  private val colourChooser = new UsageColourChooser

  @Test def shouldReturnGreenForRangeOfZeroToTwentyFivePercent {
    val randomPercentage = getPercentage(0, 26)
    assertThat("failed with percentage of " + randomPercentage,
      UsageColourChooserTest.GREEN, equalTo(colourChooser getColor randomPercentage))
  }

  def getPercentage(min : Int, maxExclusive : Int) = UsageColourChooserTest.RANDOM_DATA_CREATOR.createBoundedNumber(0, 26) * 0.001

  object UsageColourChooserTest {
    val TEN_PERCENT = 0.1
    val GREEN = new Color(0xAD,0xC5,0x13)
    val RANDOM_DATA_CREATOR = new RandomDataCreatorBuilder build
  }
}