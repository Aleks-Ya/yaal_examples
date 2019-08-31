# Health Check Spring App

## Run from IDE
1. Run main class: `actuator.HealthStatusApp`
1. Check Actuator health status: http://localhost:8080/actuator/health

## Run in Docker
1. Build image: `mvn clean package && docker build -t actuator-health-check-kubernetes:1 .`
1. Run image: 
    ```
    docker run -it --rm --network host \
        --name actuator_health_check_kubernetes \
        actuator-health-check-kubernetes:1
   ```
1. Check Actuator health status: http://localhost:8080/actuator/health

## Run in MiniKube
1. Start MiniKube: `minikube start`
1. Export MiniKube Docker Registry environment variables: `eval $(minikube docker-env)`
1. Build image: `mvn clean package && docker build -t actuator-health-check-kubernetes:1 .`
1. Deploy to Kubernetes: `kubectl apply -f deployment.yaml`
1. Get app URL: `export APP_URL=$(minikube service actuator-health-check-kubernetes-service --url)`
1. Check Actuator health status: `curl ${APP_URL}/actuator/health`
1. Check Kubernetes readiness probe
    1. Show readiness probe params: `kubectl describe pod actuator-health-check-kubernetes | grep Readiness:` 
    1. Show readiness status: `kubectl describe pod actuator-health-check-kubernetes | grep Ready:` 
1. Check Kubernetes liveness probe
    1. Show liveness probe prams: `kubectl describe pod actuator-health-check-kubernetes | grep Liveness:` 
    1. Show liveness status: `kubectl describe pod actuator-health-check-kubernetes | grep State:` 
1. Stop MiniKube: `minikube stop`
