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
import SimpleClient._

/**
 * A simple commandline client for the {@code BigpondConnector}.
 */
final class SimpleClient {

  def retrieveUsage(connector : BigpondConnector, logger : Logger) {
    try {
      val startTime = getCurrentTime
      printConnecting
      val usage = getUsage(connector)
      printUsage(usage, logger)
      printTimeTaken(startTime, logger)
    } catch {
      case e : Exception => printException(logger, e)
    }
  }

  private def printConnecting { println("Connecting...") }

  private def getUsage(connector : BigpondConnector) = {
    connector.connect()
  }

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

  private def printTimeTaken(startTime : Long, logger : Logger) {
    logger info("Time taken: " + getTimeTaken(startTime) + " seconds");
    logger info "";
  }

  private def printException(logger : Logger, e : Exception) {
      logger addHandler new ConsoleHandler
      logger severe "There seems to have been a problem. See below for details."
      logger severe ""
      logger severe e.getMessage()
      logger severe ""
      logger log(Level.SEVERE, "Detailed error report", e)
  }
}
private object SimpleClient {

    private val MS_IN_SECOND : Int  = 1000;

    private def getCurrentTime = System.currentTimeMillis

    private def getTimeTaken(startTime : Long) = (getCurrentTime - startTime) / MS_IN_SECOND
}