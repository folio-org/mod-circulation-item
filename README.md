# mod-circulation-item

Copyright (C) 2022 The Open Library Foundation

This software is distributed under the terms of the Apache License,
Version 2.0. See the file "[LICENSE](LICENSE)" for more information.

## Table of Contents

## Table of Contents

- [Introduction](#introduction)
- [Installing and deployment](#installing-and-deployment)
  - [Compiling](#compiling)
  - [Running it](#running-it)
  - [Docker](#docker)
  - [Module descriptor](#module-descriptor)
  - [Environment variables](#environment-variables)
- [Additional information](#Additional-information)
  - [Issue tracker](#issue-tracker)
  - [API documentation](#api-documentation)
  - [Code analysis](#code-analysis)
  - [Other documentation](#other-documentation)

## Introduction

FOLIO compatible persistent storage module for storing DCB item information.

## Installing and deployment

### Compiling

Compile with
```shell
mvn clean install
```

### Running it

Run locally on listening port 8081 (default listening port):

Using Docker to run the local stand-alone instance:

```shell
DB_HOST=localhost DB_PORT=5432 DB_DATABASE=okapi_modules DB_USERNAME=folio_admin DB_PASSWORD=folio_admin \
   java -Dserver.port=8081 -jar target/mod-circulation-item-*.jar
```

### Docker

Build the docker container with:

```shell
docker build -t dev.folio/mod-circulation-item .
```

### Module Descriptor

See the built `target/ModuleDescriptor.json` for the interfaces that this module
requires and provides, the permissions, and the additional module metadata.

### Environment variables

| Name                   |    Default value    | Description                                                                                                                                                                |
|:-----------------------|:-------------------:|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| DB_HOST                |      postgres       | Postgres hostname                                                                                                                                                          |
| DB_PORT                |        5432         | Postgres port                                                                                                                                                              |
| DB_USERNAME            |     folio_admin     | Postgres username                                                                                                                                                          |
| DB_PASSWORD            |          -          | Postgres username password                                                                                                                                                 |
| DB_DATABASE            |    okapi_modules    | Postgres database name                                                                                                                                                     |
| ENV                    |        folio        | Environment. Logical name of the deployment, must be set if Kafka/Elasticsearch are shared for environments, `a-z (any case)`, `0-9`, `-`, `_` symbols only allowed        |
| DB_QUERYTIMEOUT        |        60000        | Username of the system user                                                                                                                                                |
| SYSTEM\_USER\_PASSWORD |          -          | Password of the system user                                                                                                                                                |
| ACTUATOR\_EXPOSURE     | health,info,loggers | Back End Module Health Check Protocol                                                                                                                                      |

### Issue tracker

See project [MODCITEM](https://issues.folio.org/projects/MODCITEM)
at the [FOLIO issue tracker](https://dev.folio.org/guidelines/issue-tracker).

### ModuleDescriptor

See the built `target/ModuleDescriptor.json` for the interfaces that this module
requires and provides, the permissions, and the additional module metadata.

### API documentation

This module's [API documentation](https://dev.folio.org/reference/api/#mod-circulation-item).

### Code analysis

[SonarQube analysis](https://sonarcloud.io/project/overview?id=org.folio:mod-circulation-item).

## Other documentation

The built artifacts for this module are available.
See [configuration](https://dev.folio.org/download/artifacts) for repository access,
and the [Docker image](https://hub.docker.com/r/folioci/mod-circulation-item). Look at contribution guidelines [Contributing](https://dev.folio.org/guidelines/contributing).
