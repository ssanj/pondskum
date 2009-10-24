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

import org.hamcrest.core.IsEqual.equalTo
import org.junit.{Test}
import org.junit.Assert._
import UsageColourChooserObject.getRandomPercentage

class UsageColourChooserWithIllegalBoundsTest {

  val colourChooser = new UsageColourChooser

  @Test
  def shouldThrowAnExceptionForARangeOutsideAHundred {
    val randomPercentage = getRandomPercentage(101, 500)
    try {
      colourChooser getColor randomPercentage
      fail("Expected UnknownUsagePercentageException for % of " + randomPercentage)
    } catch {
      case e : UnknownUsagePercentageException => assertThat("unhandled % of " + randomPercentage, equalTo(e.getMessage))
    }
  }

}