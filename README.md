# Installation

```mysql
CREATE SCHEMA test;
USE test;
CREATE TABLE t1 (
  id BIGINT UNSIGNED AUTO_INCREMENT,
  message VARCHAR(20) NOT NULL,
  PRIMARY KEY (id)
);
# INSERT INTO t1 (message) VALUES ('Hello, World!');
# SELECT message, COUNT(message) as 'count' FROM t1 GROUP BY message;
```

Duplicate the following files and add the correct values:

`naive-spring-boot/src/main/resources/application.tmpl.properties => naive-spring-boot/src/main/resources/application.properties`

and

`naive-node-express/mysqlConnectionSettings.tmpl.json => naive-node-express/mysqlConnectionSettings.json`

# Endpoints

| Endpoint | Load        | Description                                                                                           |
|----------|-------------|-------------------------------------------------------------------------------------------------------|
| /noop    | "CPU" Bound | Return a constant string.                                                                             |
| /cpu     | CPU Bound   | Calculate Fibonacci sequence for random number [30, 37]. Not cached, not optimized.                   |
| /sleep   | "I/O" Bound | Sleeps for 500ms then returns.                                                                        |
| /write   | I/O Bound   | Writes a random record to a MySQL DB then returns the resulting JSON including the auto increment id. |
| /read    | I/O Bound   | Runs a GROUP BY aggregation over all records in the table. Not cached. No index.                      |

# Services

## naive-spring-boot

Uses out of the box `spring-boot-starter-web` (NOT Reactive) and `spring-boot-starter-jdbc` Spring Boot starters.

Note that this implementation does use `PreparedStatements` for the `/write` endpoints which does
provide some amount of optimized performance.

## naive-node-express

Uses out of the box `express` and `mysql` npm modules.

# Testing

## Siege

I used [siege](https://www.joedog.org/siege-home/) for performance testing.

```bash
./short-perf.sh
./perf.sh
```

## Gatling

Gatling is a performance testing tool can simulate a large number of user with complex behavior. [Docs](https://gatling.io/docs/current/).

It is written and Scala, which provides type safety and great integration with IDE.

#### Start

To run the tests for naive endpoints:

```bash
cd gatling
./bin/naive
```

This will compile a jar and initiate the `naive` load tests. The simulations that are found in the `src/` directory.

To run one-off gatling commands, you can use the following script:

```
./bin/gatling <options>
```

#### Example

To run the simulation found in `src/main/scala/com/bettercloud/perf/naive/NaiveExpressSimulation.scala` execute the following command:

```
./bin/gatling -s com.bettercloud.perf.naive.NaiveExpressSimulation
```

The valid options can be seen here: https://gatling.io/docs/2.3/general/configuration/#command-line-options

