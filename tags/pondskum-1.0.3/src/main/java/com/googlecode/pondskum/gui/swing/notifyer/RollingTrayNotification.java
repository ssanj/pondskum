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
package com.googlecode.pondskum.gui.swing.notifyer;

/**
 * Encapsulates an message from its {@link TrayNotification}. Each invocation to #getNotification should return a different
 * {@link TrayNotification} instance. Instance are expected to be rolled back after some value. For instance if there are only 3
 * {@link TrayNotification} instances, when the forth message comes in the 1st {@link TrayNotification} instance will be returned.
 *
 * Eg. getNotification("message1") -> tn1,
 *     getNotification("message2") -> tn2,
 *     getNotification("message3") -> tn3,
 *     getNotification("message4") -> tn1. //rolled over.
 */
public interface RollingTrayNotification {

    TrayNotification getNotification(String message);
}
