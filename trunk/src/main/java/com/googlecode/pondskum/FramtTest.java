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
package com.googlecode.pondskum;

import java.awt.Frame;
import java.awt.Label;

public class FramtTest
{
        public static void main (String args[]) throws Exception
        {
                // Create a test frame
                Frame frame = new Frame("Hello");
                frame.add ( new Label("Minimize demo") );
                frame.pack();

                // Show the frame
                frame.setVisible (true);

                // Sleep for 5 seconds, then minimize
                Thread.sleep (5000);
                frame.setState ( Frame.ICONIFIED );

                // Sleep for 5 seconds, then restore
                Thread.sleep (5000);
                frame.setState ( Frame.NORMAL );

                // Sleep for 5 seconds, then kill window
                Thread.sleep (5000);
                frame.setVisible (false);
                frame.dispose();

                // Terminate test
                System.exit(0);
        }
}