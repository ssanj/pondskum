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
package com.googlecode.pondskum.gui.swing.suite;

import com.googlecode.pondskum.client.BigpondUsageInformation;

/**
 * Defines how a gui controller should behave. The controller is a controller in an MVC pattern and as such handles all user input and
 * notifications to the various guis.
 */
public interface GuiController {

    /**
     * Notifies the implementation of this interface that the status has changed and should be updated in the views.
     * @param status The update to display in the views.
     */
    void notifyStatusChange(String status);

    /**
     * The connection completed successfully and the usage is ready for retrieval.
     * @param bigpondUsageInformation The usage information from the connection.
     */
    void connectionSucceeded(BigpondUsageInformation bigpondUsageInformation);

    /**
     * The connection failed and the exception that occurred is ready for retrieval.
     * @param exception The exception that occurred.
     */
    void connectionFailed(Exception exception);
}
