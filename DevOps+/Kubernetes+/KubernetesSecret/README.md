!!! Not Finished

# Use Kubernetes Secrets from Java application

## Run unit tests
`mvn clean test`

## Run locally from Idea
Run class `ru.yaal.examples.kubernates.configmap.PrintEnvVariables`

## Run locally from Docker image
Build project and Docker image: `mvn clean package` 
Run container: `docker run --rm -it kubernates-config-map:1`

## Execute on MiniKube
Set environment variables: `eval $(minikube docker-env)`
Build project and Docker image: `mvn clean package`
Create deployment and service: `kubectl create -f deployment.yaml`
Test: `kubectl logs -l=app=kubernates-config-map`
Delete deployment and service: `kubectl delete -f deployment.yaml`


```
echo "my_username" > username.txt
echo "my_password" > password.txt
kubectl create secret generic db-user-pass --from-file=./username.txt --from-file=./password.txt

```