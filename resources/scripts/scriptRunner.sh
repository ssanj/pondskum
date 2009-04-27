#! /bin/sh
#---------------------------------------------------------------------------------------
# Exports the classpath and configuration information needed to run gui clients.
#---------------------------------------------------------------------------------------

echo "setting configuration parameters"

export BIGPOND_CONFIG_FILE_LOCATION=../etc

if [ ! -e "$BIGPOND_CONFIG_FILE_LOCATION/bigpond_config.properties" ]; then
    echo "ERROR: Could not find $BIGPOND_CONFIG_FILE_LOCATION/bigpond_config.properties file."
    echo "Have you created a config file from $BIGPOND_CONFIG_FILE_LOCATION/bigpond_config.sample?"
    exit 0
fi

export JAVA_CMD_CP=../etc:../lib/httpclient-4.0-beta2.jar:../lib/httpcore-4.0-beta3.jar:../lib/commons-logging-1.1.1.jar:../lib/commons-codec-1.3.jar:../lib/htmlparser-1.6.jar:../lib/pinthura-core-0.0.1-SNAPSHOT.jar:../lib/cglib-nodep-2.1_3.jar:../lib/slf4j-api-1.5.2.jar:../lib/slf4j-simple-1.5.2.jar:../lib/pondskum-1.0.jar

./$1
