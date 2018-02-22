#!/bin/sh

echo "********************************************************"
echo "Starting OAuth2 Server with Configuration Service :  $CONFIGSERVER_URI";
echo "********************************************************"
java -jar /usr/local/oauth2server/@project.build.finalName@.jar
