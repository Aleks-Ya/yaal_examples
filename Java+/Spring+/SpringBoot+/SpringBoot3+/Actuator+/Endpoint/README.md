# Spring Boot Actuator endpoints

## Endpoints

### All
Main class: `actuator.endpoint.all.AllApp`

### Health
Main class: `actuator.endpoint.health.HealthApp`
Health status: http://localhost:8080/actuator/health

### Loggers
Main class: `actuator.endpoint.loggers.LoggersApp`
Using:
1. List all loggers: http://localhost:8080/actuator/loggers
2. Show details of `actuator.endpoint.loggers.LoggersApp` logger: http://localhost:8080/actuator/loggers/actuator.endpoint.loggers.LoggersApp
3. Show details of `ROOT` logger: http://localhost:8080/actuator/loggers/ROOT
4. Set `TRACE` level for `ROOT` logger:
    ```
    curl -i -X POST \
        -H 'Content-Type: application/json' \
        -d '{"configuredLevel": "TRACE"}' \
        http://localhost:8080/actuator/loggers/ROOT
   ```

### Env
Main class: `actuator.endpoint.env.EnvApp`
Web: http://localhost:8080/actuator/env