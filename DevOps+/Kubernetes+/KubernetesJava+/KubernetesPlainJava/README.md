# Kubernates Plain Java Application

## Run unit tests
`mvn clean test`

## Run locally from Idea
Run class `ru.yaal.examples.kubernates.plainjava.PrintHelloToConsole`

## Run locally from Docker image
Build project and Docker image: `mvn clean package` 
Run container: `docker run --rm -it kubernates-plain-java`

## Execute on MiniKube
Set environment variables: `eval $(minikube docker-env)`
Build project and Docker image: `mvn clean package`
Create deployment and service: `kubectl create -f deployment.yaml`
Test: `kubectl logs -l=app=kubernates-plain-java`
Delete deployment and service: `kubectl delete -f deployment.yaml`