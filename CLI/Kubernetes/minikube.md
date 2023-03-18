# minikube CLI

Docs: [Running Kubernetes Locally via Minikube](https://kubernetes.io/docs/setup/minikube/)

## Install
1. `brew install minikube`
2. Close the terminal
3. Check: `minikube version`

## Cluster
- Run cluster: `minikube start --driver=docker`
- Run specific version of Kubernates cluster:  `minikube start --kubernetes-version v1.7.3`
- Connect to `kubectl`: just use host `kubectl` (autoconfigured to work with MiniKube)
- Delete cluster (including VirtualBox VM): `minikube delete`
- Delete cluster (entire `~/.minikube` dir): `minikube delete --purge`
- Stop cluster: `minikube stop`

## Service
- List all services: `minikube service list`
- Get URL of a service: `minikube service hello-minikube --url`

## Docker images
- List Docker images: `minikube image ls`
- Load an image from local Docker registry to Minikube Docker Registry: `minikube image load centos:8`

## Other
- Help: `minikube -h`
- Show status: `minikube status`
- Run Kubernates Dashboard: `minikube dashboard`
- Show MiniKuber version: `kubectl version`
- SSH: `minikube ssh`
