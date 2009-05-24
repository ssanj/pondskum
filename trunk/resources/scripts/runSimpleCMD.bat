echo "setting configuration parameters"

set BIGPOND_CONFIG_FILE_LOCATION=..\etc
set JAVA_CMD_CP=..\etc;..\lib\httpclient-4.0-beta2.jar;..\lib\httpcore-4.0-beta3.jar;..\lib\commons-logging-1.1.1.jar;..\lib\commons-codec-1.3.jar;..\lib\htmlparser-1.6.jar;..\lib\pinthura-core-0.0.1-SNAPSHOT.jar;..\lib\cglib-nodep-2.1_3.jar;..\lib\slf4j-api-1.5.2.jar;..\lib\slf4j-simple-1.5.2.jar;..\lib\pondskum-1.0.jar

java -Dbigpond.config.location=%BIGPOND_CONFIG_FILE_LOCATION% -classpath %JAVA_CMD_CP% com.googlecode.pondskum.gui.simplecmd.SimpleCMD