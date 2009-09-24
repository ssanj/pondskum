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


import bootstrap.PondskumModule
import google.inject.Guice
import client.BigpondConnector
import logger.LogProvider

object SimpleClientRunner {

  def main(args : Array[String]) {
    val injector = Guice.createInjector(new PondskumModule)
    val logger = injector.getInstance(classOf[LogProvider]).provide(classOf[SimpleCMDRunner])
    val connector = injector.getInstance(classOf[BigpondConnector])
    val connectionPrinter = injector.getInstance(classOf[ConnectionPrinter])

    new SimpleClient printUsage(connector, connectionPrinter)
  }
}