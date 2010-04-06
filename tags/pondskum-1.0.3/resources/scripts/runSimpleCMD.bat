@echo off
REM Copyright 2008 Sanjiv Sahayam
REM
REM Licensed under the Apache License, Version 2.0 (the "License");
REM you may not use this file except in compliance with the License.
REM You may obtain a copy of the License at
REM
REM     http://www.apache.org/licenses/LICENSE-2.0
REM
REM Unless required by applicable law or agreed to in writing, software
REM distributed under the License is distributed on an "AS IS" BASIS,
REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
REM See the License for the specific language governing permissions and
REM limitations under the License.
@echo on

echo "setting configuration parameters"

set BIGPOND_CONFIG_FILE_LOCATION=..\etc
set JAVA_CMD_CP=..\etc;..\lib\httpclient-4.0-beta2.jar;..\lib\httpcore-4.0-beta3.jar;..\lib\commons-logging-1.1.1.jar;..\lib\commons-codec-1.3.jar;..\lib\htmlparser-1.6.jar;..\lib\pinthura-core-0.0.2-SNAPSHOT.jar;..\lib\cglib-nodep-2.1_3.jar;..\lib\slf4j-api-1.5.2.jar;..\lib\slf4j-simple-1.5.2.jar;..\lib\${artifactId}-${version}.jar

java -Dbigpond.config.location=%BIGPOND_CONFIG_FILE_LOCATION% -classpath %JAVA_CMD_CP% com.googlecode.pondskum.gui.simplecmd.SimpleCMDRunner