##### Table of Contents

* [Description](#description)
* [Assumptions](#assumptions)
* [Dependencies](#dependencies)
* [Instructions to run](#instructions-to-run)
* [Documentation](#documentation)
* [Testing](#testing)
* [Database](#database)
* [Messaging Queue](#rabbitmq)
* [Logging/Monitoring](#loggingmonitoring)

<a name="description"></a>

## Description

Backend messaging application assignment based on spring boot and rabbitmq

<a name="assumptions"></a>

## Assumptions

* NA

<a name="dependencies"></a>

## Dependencies

* [Maven](https://maven.apache.org/)
* [RabbitMq](https://github.com/rabbitmq/rabbitmq-server/releases/download/v3.8.8/rabbitmq-server-3.8.8.exe)

<a name="instructions-to-run"></a>

## Instructions to run


1.  Open project in any IDE as a maven project. Build the application:

```bash
 mvn clean install
```

2. Build docker image:

```bash
 docker build -t docker-spring-boot-postgres:latest
``` 

3. Compose and run the docker container :

```bash
docker-compose up --force-recreate
``` 

<a name="documentation"></a>

## Documentation

OpenAPI Specification Swagger documentation is at the default page

When running on local machine[http://localhost:8080/](http://localhost:8080/)
When running on Docker [http://localhost:8081/](http://localhost:8081/)

<a name="testing"></a>

## Testing

Junit test cases to check auth and test APIs

<a name="database"></a>

## Database

H2 Database

<a name="rabbitmq"></a>

## Messaging Queue

RabbitMQ is used to publish sent messages and consume to write in logs and DB

1. Post methods on message send api publishes new message to RabbitMQ exchange
2. Published message is routed to queue
3. This message is then consumed via consumer and is written to log file (logs/MessageQueLogFile.log)as well as H2 Db

<a name="security"></a>



<a name="loggingmonitoring"></a>
## Logging/Monitoring
[Lombok](https://projectlombok.org/) Slf4j logging is used for the ease of implementation.

