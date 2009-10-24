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


import client.{BigpondConnector}
/**
 * A simple commandline client for the {@code BigpondConnector}.
 */
final class SimpleClient {

  /**
   * Retrieves the current bigpond usage and prints it to the console. Any exceptions occur are printed.
   *
   * @param connector The {@code BigpondConnector} to use when retrieving usage information.
   * @param forConnection A function that takes in a {@code BigpondConnector} and returns its data as a {@code String}.
   * @param printer The {@code Printer} to use to print status information.
   */
  def printUsage(connection : BigpondConnector, forConnection : BigpondConnector => String, printer : Printer) {
    try {
      printer.printUsage(forConnection(connection))
    } catch {
      case e : Exception => printer printException(e)
    } finally {
      printer printTimeTaken
    }
  }
}