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

import java.awt.Color
import pinthura.util.builder.RandomDataCreatorBuilder

object UsageColourChooserObject {

    private val randomDataCreator = createRandomDataCreator

    def greenColour = new Color(0xAD,0xC5,0x13)

    def yellowColour = new Color(0xFF, 0xCC, 0x05)

    def orangeColour = new Color(0xFF, 0x5F, 0x05)

    def darkRedColour = new Color(0xC5, 0x28, 0x13)

    def brightRedColour = new Color(0xFC, 0x24, 0x06)

    def getRandomPercentage(min : Int, max : Int) = asPercentage(randomDataCreator.createBoundedNumber(min, plusOne(max)))

    private def asPercentage(value : Int) = 0.01 * value

    private def createRandomDataCreator = new RandomDataCreatorBuilder build

    private def plusOne(number : Int) = number + 1
  }