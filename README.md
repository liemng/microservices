# microservices
## Requirements
* Requires Docker, Java 8, and Maven
* At run-time, fetches configurations from [Configuration Github](https://github.com/liemng/microconfig)

## Build Instructions
`mvn clean package docker:build`

## Deploy Instructions
`docker-compose -f docker-gw.yml -f docker-infra.yml -f docker-app.yml up`

_Note_: The microservices utilize the CA Technologies API Gateway and requires a valid `LICENSE.xml` file in the ./apigw directory to work.

## Clean-up Instructions
`docker-compose -f docker-gw.yml -f docker-infra.yml -f docker-app.yml down && docker rmi $(docker images | awk '$2~/microservices/{print $3}')`

## Hacking Instructions
The API Gateway and infrastructure containers need to be started on Docker:<p>
`docker-compose -f docker-gw.yml -f docker-infra.yml`<p>
The rest of the application, i.e., those in the `docker-app.yml` file can be run locally in your favorite IDE for debugging.<p>
_Note_: For eventing, modify the `docker-infra.yml` for the Kafka server to advertise on localhost.