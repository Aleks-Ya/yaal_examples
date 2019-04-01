# Run Python script in Kubernates pod

## Build image
Set environment variables: `eval $(minikube docker-env)`
Build project and Docker image: `docker build -t kubernates-python:1 .`
Run locally: `docker run --rm -ti kubernates-python:1`

## Deploy to MiniKube
Deploy: `kubectl apply -f deployment.yaml`
Test: `kubectl logs -l=app=python-hello-world`
Delete deployment: `kubectl delete deployment python-hello-world-deployment`
