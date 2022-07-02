# Deploy a Spring Boot app with Helm


## Build a Docker image
1. `cd PeopleApp`
2. Jar: `gradle clean build`
2. Docker image: 
   1. `eval $(minikube docker-env)` (once, for using Minikube Registry)
   2. `docker build -t people-app .`

## Install Helm release
Install: `helm install people-app .`
Show status: `helm status people-app`
Uninstall: `helm uninstall people-app`
