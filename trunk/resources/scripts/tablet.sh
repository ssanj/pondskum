#! /bin/sh

echo "Bigpond file location=$BIGPOND_CONFIG_FILE_LOCATION"
export JAVA_CMD_CP=$JAVA_CMD_CP:../lib/core-8.9732.jar
echo "classpath=$JAVA_CMD_CP"

java -Dbigpond.config.location=$BIGPOND_CONFIG_FILE_LOCATION -classpath $JAVA_CMD_CP com.googlecode.pondskum.gui.swing.tablet.TabletRunner