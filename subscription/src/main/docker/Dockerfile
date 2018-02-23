FROM openjdk:8-jdk-alpine
RUN  apk update && apk upgrade && apk add curl
RUN mkdir -p /usr/local/subscriptionservice
ADD  @project.build.finalName@.jar /usr/local/subscriptionservice/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh