#! /bin/sh

echo "Bigpond file location=$BIGPOND_CONFIG_FILE_LOCATION"
echo "classpath=$JAVA_CMD_CP"

java -Dbigpond.config.location=$BIGPOND_CONFIG_FILE_LOCATION -classpath $JAVA_CMD_CP com.googlecode.pondskum.gui.swing.tablet.TabletRunner
