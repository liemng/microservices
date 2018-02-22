#!/bin/sh

echo "********************************************************"
echo "Starting OAuth2 Server";
echo "********************************************************"
java -jar /usr/local/oauth2server/@project.build.finalName@.jar
