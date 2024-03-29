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

/**
 * Choose the subsequenty gui to display.
 */
public interface GuiChooser extends GUI.StateChangeListener {

    /**
     * Returns the current gui.
     * @return The current gui.
     */
    GUI getGui();

    /**
     * Initialises the current gui so it can be reused for reconnection updates.
     */
    void initialiseGui();

}
