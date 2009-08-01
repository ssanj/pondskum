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

public final class ProgressTrayIconRunner {

    public static void main(String[] args) {
        new ProgressTrayIconSwingWorker().execute(); // move this out once we have the timer reoccurring.
    }

//    private static void createTimer() {
//         final Timer timer = new Timer(0, null);
//         timer.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(final ActionEvent e) {
//
//                 new ProgressTrayIconSwingWorker().execute(); // move this out once we have the timer reoccurring.
//             }
//         });
//
//         timer.setInitialDelay(0);//start immediately.
//         timer.setRepeats(false);
//         timer.setCoalesce(true); //send only 1 event even if multiple are queued.
//         timer.start();
//    }
}
