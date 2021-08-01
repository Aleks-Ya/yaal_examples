# Run Python script in Kubernates pod

## Build image
1. Set environment variables: `eval $(minikube docker-env)`
2. Build project and Docker image: `docker build -t kubernates-python:1 .`
3. (optional) Run locally: 
    1. HelloWorld: `docker run --rm -ti kubernates-python:1 python hello_world.py`
    2. HttpServer: `docker run --rm -ti kubernates-python:1 python http_server.py`

## Deploy to MiniKube
Deploy: `kubectl apply -f deployment.yaml`
Test: 
    1. HelloWorld: `kubectl logs -f -l=app=python-hello-world`
    2. HttpServer: 
        1. Logs: `kubectl logs -f -l=app=python-http-server`
        2. Browser:
            1. Expose port: `kubectl port-forward deployment/python-http-server-deployment 8000:8000`
            2. Open http://localhost:8000
Delete deployment: `kubectl delete -f deployment.yaml`

