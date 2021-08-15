# Kubernates Spring Boot Application

## Run unit tests
`mvn clean test`

## Run locally from Idea
Run class `ru.yaal.examples.kubernates.springboot.KubernatesSpringBootApplication`

Test `curl localhost:8080/hello`

## Run locally from Docker image
Build project and Docker image: `mvn clean package` 
Run container: `docker run --rm -it kubernates-spring-boot`

## Execute on MiniKube
Set environment variables: `eval $(minikube docker-env)`
Build project and Docker image: `mvn clean package`
Create deployment and service: `kubectl create -f deployment.yaml`
Test: `curl $(minikube service kubernates-spring-boot-service --url)/hello`
Delete deployment and service: `kubectl delete -f deployment.yaml`