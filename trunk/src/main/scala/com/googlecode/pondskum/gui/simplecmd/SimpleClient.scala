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


import client.{BigpondConnector, BigpondUsageInformation}
import java.util.logging.{ConsoleHandler, Level, Logger}
/**
 * A simple commandline client for the {@code BigpondConnector}.
 */
final class SimpleClient {

  /**
   * Retrieves the current bigpond usage and prints it to the console. If an exception occurs, it is logged to the default logger
   * passed in.
   * @param connector The {@code BigpondConnector} to use when retrieving usage information.
   * @param logger The {@code Logger} to use to log an exceptions.
   */
  def printUsage(connector : BigpondConnector, logger : Logger) {
    try {
      val timeKeeper = new TimeKeeper
      printConnecting
      val usage = getUsage(connector)
      printUsage(usage, logger)
      printTimeTaken(timeKeeper, logger)
    } catch {
      case e : Exception => printException(e, logger)
    }
  }

  private def printConnecting { println("Connecting...") }

  private def getUsage(connector : BigpondConnector) = { connector.connect() }

  private def printUsage(usageInformation : BigpondUsageInformation, logger : Logger) {
    println ("")
    println(new UsageMessageBuilder
            withUsageInformation(usageInformation)
            displayAccountName()
            displayTotalUsage()
            displayDownloadUsage()
            displayUploadUsage()
            build);
  }

  private def printTimeTaken(timeKeeper : TimeKeeper, logger : Logger) {
    logger info("Time taken: " + timeKeeper.getTimeTaken + " seconds");
    logger info "";
  }

  private def printException(e : Exception, logger : Logger) {
      logger addHandler new ConsoleHandler
      logger severe "There seems to have been a problem. See below for details."
      logger severe ""
      logger severe e.getMessage()
      logger severe ""
      logger log(Level.SEVERE, "Detailed error report", e)
  }
}