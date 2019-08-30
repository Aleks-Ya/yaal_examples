# Spring Boot Actuator "/loggers" endpoint

## Run 
Main class: `actuator.LoggersApp` 

 ## Endpoints
1. List all loggers: http://localhost:8080/actuator/loggers
1. Show details of `actuator.LoggersApp` logger: http://localhost:8080/actuator/loggers/actuator.LoggersApp
1. Show details of `ROOT` logger: http://localhost:8080/actuator/loggers/ROOT
1. Set `TRACE` level for `ROOT` logger:
    ```
    curl -i -X POST \
        -H 'Content-Type: application/json' \
        -d '{"configuredLevel": "TRACE"}' \
        http://localhost:8080/actuator/loggers/ROOT
   ```