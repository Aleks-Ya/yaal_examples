# Health Check Spring App

## Run from IDE

1. Run main class: `actuator.HealthStatusApp`
1. Check Actuator health status: `curl http://localhost:9999/actuator/health`
1. Check web page: `curl http://localhost:8080/index.html`

## Run in Docker

1. Build image: `mvn clean package && docker build -t actuator-health-check-kubernetes:1 .`
1. Run image:
    ```
    docker run -it --rm --network host \
        --name actuator_health_check_kubernetes \
        actuator-health-check-kubernetes:1
   ```
1. Check Actuator health status: `curl http://localhost:9999/actuator/health`
1. Check web page: `curl http://localhost:8080/index.html`

## Run in MiniKube

1. Start MiniKube: `minikube start --driver=docker`
1. Export MiniKube Docker Registry environment variables: `eval $(minikube docker-env)`
1. Build image: `mvn clean package && docker build -t actuator-health-check-kubernetes:1 .`
1. Deploy to Kubernetes: `kubectl apply -f deployment.yaml`
1. Get Actuator URL: `export ACTUATOR_URL=http://$(minikube ip):32099/actuator/health`
1. Get HTTP URL: `export HTTP_URL=http://$(minikube ip):32080/index.html`
1. Check Actuator health status: `curl $ACTUATOR_URL`
1. Check HTTP: `curl $HTTP_URL`
1. Check Kubernetes readiness probe
    1. Show readiness probe params: `kubectl describe pod actuator-health-check-kubernetes | grep Readiness:`
    1. Show readiness status: `kubectl describe pod actuator-health-check-kubernetes | grep Ready:`
1. Check Kubernetes liveness probe
    1. Show liveness probe params: `kubectl describe pod actuator-health-check-kubernetes | grep Liveness:`
    1. Show liveness status: `kubectl describe pod actuator-health-check-kubernetes | grep State:`
1. Delete app: `kubectl delete -f deployment.yaml`
1. Stop MiniKube: `minikube stop`
