# Parallel Test Execution with Maven (JUnit 5)

## Documentation
["Parallel Execution" in JUnit Docs](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parallel-execution)

## Run
### Single thread run
- `mvn clean test`
- `mvn -Djunit.jupiter.execution.parallel.enabled=false clean test`  

### Parallel run
Parallel methods: `mvn -Djunit.jupiter.execution.parallel.enabled=true clean test`
In `pom.xml`:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0-M4</version>
    <configuration>
        <properties>
            <configurationParameters>
                junit.jupiter.execution.parallel.enabled = true
            </configurationParameters>
        </properties>
    </configuration>
</plugin>
```