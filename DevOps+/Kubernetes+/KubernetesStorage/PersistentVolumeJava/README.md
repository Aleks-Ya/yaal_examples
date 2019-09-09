# Kubernates PersistentVolume Java Application

## Run unit tests
`mvn clean test`

## Run locally from Idea
Run class `storage.WriteToVolumeAndWait`

## Run locally from Docker image
Build project and Docker image: `mvn clean package` 
Run container: `docker run --rm -it persistent-volume-java`

## Execute on MiniKube
Run MiniKube: `minikube start`
Set environment variables: `eval $(minikube docker-env)`
Build project and Docker image: `mvn clean package`
Create deployment and service: `kubectl create -f deployment.yaml`
Test: `kubectl logs -l=app=persistent-volume-java`
Delete deployment and service: `kubectl delete -f deployment.yaml`
Stop MiniKube: `minikube stop`
