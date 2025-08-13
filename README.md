# ATDD
Acceptance Test Driven Development (ATDD)

[![Java CI with Gradle](https://github.com/optivem/atdd/actions/workflows/gradle.yml/badge.svg)](https://github.com/optivem/atdd/actions/workflows/gradle.yml)

This repository is under development. Please don't rely on it yet.

## License

MIT Licence

## Local Execution

Ensure Docker Deskop is running.
Make sure you've setup JAVA_HOME and added the `bin` directory to your `PATH`.
We're using JDK 24.

```shell
docker compose up -d
./gradlew build
```

For debugging:

```shell
./gradlew build --info --stacktrace
```