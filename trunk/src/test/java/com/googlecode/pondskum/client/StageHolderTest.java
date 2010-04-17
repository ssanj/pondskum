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

import org.junit.Test;

import static com.googlecode.pondskum.client.ConnectionStage.CLOSE_REQUEST_FOR_LOGIN;
import static com.googlecode.pondskum.client.ConnectionStage.CLOSE_REQUEST_FOR_LOGOUT;
import static com.googlecode.pondskum.client.ConnectionStage.CLOSE_REQUEST_FOR_USAGE;
import static com.googlecode.pondskum.client.ConnectionStage.OPEN_REQUEST_FOR_LOGIN;
import static com.googlecode.pondskum.client.ConnectionStage.OPEN_REQUEST_FOR_LOGOUT;
import static com.googlecode.pondskum.client.ConnectionStage.OPEN_REQUEST_FOR_USAGE;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public final class StageHolderTest {

    @Test
    public void shouldThrownAnExceptionIfThereAreLessThanTwoStates() {
        try {
            new StageHolder(CLOSE_REQUEST_FOR_LOGIN);
            fail("Expected an IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), equalTo("The minimum size of a StateHolder is 2."));
        }
    }

    @Test public void shouldHandleTwoStates() {
        StageHolder holder = new StageHolder(CLOSE_REQUEST_FOR_LOGIN, CLOSE_REQUEST_FOR_LOGOUT);
        assertThat(holder.getState(), equalTo(CLOSE_REQUEST_FOR_LOGIN));
        holder.nextState();
        assertThat(holder.getState(), equalTo(CLOSE_REQUEST_FOR_LOGOUT));
    }

    @Test public void shouldHandleMultipleCallsToNextStateWhenThereAreNoStatesLeft() {
        StageHolder holder = new StageHolder(OPEN_REQUEST_FOR_LOGIN, CLOSE_REQUEST_FOR_LOGIN);
        assertThat(holder.getState(), equalTo(OPEN_REQUEST_FOR_LOGIN));
        holder.nextState();
        assertThat(holder.getState(), equalTo(CLOSE_REQUEST_FOR_LOGIN));
        holder.nextState();
        holder.nextState();
        assertThat(holder.getState(), equalTo(CLOSE_REQUEST_FOR_LOGIN));
    }

    @Test public void shouldHandleAllStates() {
        StageHolder holder =
                new StageHolder(OPEN_REQUEST_FOR_LOGIN,
                                CLOSE_REQUEST_FOR_LOGIN,
                                OPEN_REQUEST_FOR_USAGE,
                                CLOSE_REQUEST_FOR_USAGE,
                                OPEN_REQUEST_FOR_LOGOUT,
                                CLOSE_REQUEST_FOR_LOGOUT);
        assertThat(holder.getState(), equalTo(OPEN_REQUEST_FOR_LOGIN));
        holder.nextState();
        assertThat(holder.getState(), equalTo(CLOSE_REQUEST_FOR_LOGIN));
        holder.nextState();
        assertThat(holder.getState(), equalTo(OPEN_REQUEST_FOR_USAGE));
        holder.nextState();
        assertThat(holder.getState(), equalTo(CLOSE_REQUEST_FOR_USAGE));
        holder.nextState();
        assertThat(holder.getState(), equalTo(OPEN_REQUEST_FOR_LOGOUT));
        holder.nextState();
        assertThat(holder.getState(), equalTo(CLOSE_REQUEST_FOR_LOGOUT));
    }
}
