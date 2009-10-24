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
package com.googlecode.pondskum.gui.simplecmd


import java.util.logging.{ConsoleHandler, Level, Logger}

/**
 * Prints the connection status of a Bigpond account.
 */
final class DefaultConnectionPrinter(logger : Logger) extends ConnectionPrinter {

  val timeKeeper = TimeKeeper.create
  println("Connecting...")

   override def printTimeTaken {
    logger info("Time taken: " + timeKeeper.getTimeTaken + " seconds")
    logger info ""
  }

  override def printException(e : Exception) {
      logger addHandler new ConsoleHandler
      logger severe "There seems to have been a problem. See below for details."
      logger severe ""
      logger severe e.getMessage
      logger severe ""
      logger log(Level.SEVERE, "Detailed error report", e)
  }
}