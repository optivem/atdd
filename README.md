# ATDD
Acceptance Test Driven Development (ATDD)

[![Java CI with Gradle](https://github.com/optivem/atdd/actions/workflows/gradle.yml/badge.svg)](https://github.com/optivem/atdd/actions/workflows/gradle.yml)

This repository is under development. Please don't rely on it yet.

## License

MIT Licence

## Installation

1. Ensure Docker Deskop is running.
2. Install JDK 24. 
3. Setup JAVA_HOME environment variable to point to your JDK 24 installation.
4. Add the JDK `bin` directory to your `PATH` environment variable (on Windows `%JAVA_HOME%\bin`, on Linux `$JAVA_HOME/bin`).

## Usage

Run Docker Compose to run the application:
```shell
docker compose up -d
```

Compile and run tests using Gradle:

```shell
./gradlew build
```

For more detailed output:

```shell
./gradlew build --info --stacktrace
```