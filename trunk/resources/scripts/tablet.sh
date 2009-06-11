#! /bin/sh
# Copyright 2008 Sanjiv Sahayam
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

echo "Bigpond file location=$BIGPOND_CONFIG_FILE_LOCATION"
export JAVA_CMD_CP=$JAVA_CMD_CP:../lib/core-8.9732.jar
echo "classpath=$JAVA_CMD_CP"

java -Dbigpond.config.location=$BIGPOND_CONFIG_FILE_LOCATION -classpath $JAVA_CMD_CP com.googlecode.pondskum.gui.swing.tablet.TabletRunner
