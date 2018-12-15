#!/bin/sh

echo "********************************************************"
echo "Starting Subscription Server with Configuration Service: $CONFIGSERVER_URI";
echo "********************************************************"
java -Dspring.cloud.config.uri=$CONFIGSERVER_URI                       \
     -Dspring.profiles.active=$PROFILE                                 \
     -jar /usr/local/subscriptionservice/@project.build.finalName@.jar
