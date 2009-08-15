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
package com.googlecode.pondskum.gui.swing.tablet;

import com.googlecode.pondskum.client.BigpondUsageInformation;

/**
 * Allows reconnections to occur in a predefined manner.
 */
public interface Reconnectable {

    /**
     * Updates the table with updated data.
     * @param usageInformation The updated usage data.
     */
    void setTabletData(BigpondUsageInformation usageInformation);

    /**
     * Registers an update listener which is notified of table updates.
     * @param listener The listener to be notified.
     */
    void setUpdateListener(TabletUpateListener listener);

    /**
     * Calls for disabling the gui while an update is in progress.
     */
    void disableUpdates();

    /**
     * Calls for enabling the gui once the updates are complete.
     */
    void enableUpdates();

}
