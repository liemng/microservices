FROM openjdk:8-jdk-alpine
RUN  apk update && apk upgrade && apk add curl
RUN mkdir -p /usr/local/oauth2server
ADD  @project.build.finalName@.jar /usr/local/oauth2server/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh