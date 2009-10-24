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

public final class UsageTableValue {

    private static final float GIGABYTE = 1000.00f;
    private final Integer value;

    public UsageTableValue(final Integer value) {
        this.value = value;
    }

    public String getValue() {
        //TODO: format such that 1.0G is 1 G.
        return (value >= GIGABYTE) ? (value / GIGABYTE) + " G" : value + " M";
    }
}
