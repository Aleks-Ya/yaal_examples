# kubeseal CLI

## Install client
Brew: `brew install kubeseal`

## Install controller to Kubernetes cluster
By Helm: 
1. `helm repo add sealed-secrets https://bitnami-labs.github.io/sealed-secrets`
2. `helm install sealed-secrets sealed-secrets/sealed-secrets`

## Info
Version: `kubeseal --version`
