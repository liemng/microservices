#!/bin/sh

echo "********************************************************"
echo "Starting Billing Service with Configuration Service: $CONFIGSERVER_URI";
echo "********************************************************"
java -Dspring.cloud.config.uri=$CONFIGSERVER_URI                       \
     -Dspring.profiles.active=$PROFILE                                 \
     -jar /usr/local/billingservice/@project.build.finalName@.jar
