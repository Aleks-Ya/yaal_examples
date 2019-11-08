# Spring 5 on Java 13 example

## Run from CLI
1. Check Java version: `java -version` (should be 13)
1. Build: `mvn clean package`
1. Run: `java -jar target/spring-java13-example-1.jar`
1. Check: `curl http://localhost:8080/hello`

## Run from IDE
1. Choose Java 13 version for the project
1. Run main class: `app.SpringJava13ExampleApplication`
1. Check: `curl http://localhost:8080/hello`