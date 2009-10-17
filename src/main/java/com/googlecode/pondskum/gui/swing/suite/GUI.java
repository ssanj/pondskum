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

public interface GUI extends GuiController {

    /**
     * Resets components of the gui for reuse, possibly multiple times.
     */
    void resetForReuse();

    /**
     * Displays an implementation of the gui.
     */
    void display();

    /**
     * Hides this implementation of the gui. The gui should be resurrectable via {@link #display()}.
     */
    void hide();

    void setStateChangeListener(StateChangeListener stateChangeListener);

    interface StateChangeListener {

        void stateChangeOccured(GUI gui);
    }
}
