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
package com.googlecode.pondskum.client;

import java.util.Arrays;

public final class StageHolder {

    private ConnectionStage[] stages;

    public StageHolder(final ConnectionStage... stage) {
        if (stage.length < 2) {
            throw new IllegalArgumentException("The minimum size of a StateHolder is 2.");
        }

        stages = stage.clone();
    }

    public void nextState() {
        if (hasMoreStates()) {
            stages = Arrays.copyOfRange(stages, 1, stages.length);
        }
    }

    public ConnectionStage getState() {
        return stages[0];
    }

    private boolean hasMoreStates() {
        return stages.length > 1;
    }
}
