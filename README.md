# microservices
## Requirements
* Requires Docker, Java 8, and Maven
* At run-time, fetches configurations from [Configuration Github](https://github.com/liemng/microconfig)

## Build Instructions
`mvn clean package docker:build`

## Deploy Instructions
`docker-compose up`

_Note_: The microservices utilize the CA Technologies API Gateway and requires a valid `LICENSE.xml` file in the ./apigw directory to work.

## Clean-up Instructions
`docker-compose down && docker rmi $(docker images | awk '$2~/microservices/{print $3}')`