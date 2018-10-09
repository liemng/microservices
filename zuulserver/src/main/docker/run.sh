#!/bin/sh

echo "********************************************************"
echo "Starting Zuul Server with Configuration Service: $CONFIGSERVER_URI";
echo "********************************************************"
java -Dspring.cloud.config.uri=$CONFIGSERVER_URI                       \
     -Dspring.profiles.active=$PROFILE                                 \
     -jar /usr/local/zuulserver/@project.build.finalName@.jar
