# Health Check Spring App

## Run from IDE

1. Run main class: `actuator.HealthStatusApp`
1. Check Actuator health status: http://localhost:8080/actuator/health

## Run in Docker

1. Build image: `mvn clean package && docker build -t health-check-spring-app:1 .`
1. Run image:
    ```
    docker run -it --rm --network host \
        --name health_check_spring_app \
        health-check-spring-app:1
   ```
1. Check Actuator health status: http://localhost:8080/actuator/health
1. Check Docker container health status:
    1. Current status: `docker inspect -f="{{json .State.Health.Status}}" health_check_spring_app` or `docker ps`
    1. Last 5 statuses: `docker inspect -f="{{json .State.Health}}" health_check_spring_app`