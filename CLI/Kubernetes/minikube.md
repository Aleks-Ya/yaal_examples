# minikube CLI

Docs: [Running Kubernetes Locally via Minikube](https://kubernetes.io/docs/setup/minikube/)

## Cluster
- Run cluster: `minikube start --driver=docker`
- Run specific version of Kubernates cluster:  `minikube start --kubernetes-version v1.7.3`
- Delete cluster (including VirtualBox VM): `minikube delete`
- Stop cluster: `minikube stop`

## Service
- List all services: `minikube service list`
- Get URL of a service: `minikube service hello-minikube --url`

## Other
- Help: `minikube -h`
- Show status: `minikube status`
- Run Kubernates Dashboard: `minikube dashboard`
- Show MiniKuber version: `kubectl version`
- SSH: `minikube ssh`
