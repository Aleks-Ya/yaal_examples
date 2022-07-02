# Argo CD

## Install
### Server
Install all (with UI, SSO, multi-cluster):
```
kubectl create namespace argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
```
Install core only (without UI, SSO, multi-cluster):
```
kubectl create namespace argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/core-install.yaml
```
Open UI:
1. Forward UI port: `kubectl port-forward svc/argocd-server -n argocd 8080:443`
2. Open https://localhost:8080
### Get admin credentials
Username: `admin`
Show auto-generated password: `kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d; echo`
### Login CLI
`argocd login localhost:8080`
### Create a test application
Craete an application: `argocd app create guestbook --repo https://github.com/argoproj/argocd-example-apps.git --path guestbook --dest-server https://kubernetes.default.svc --dest-namespace default`
Show application status: `argocd app get guestbook`
Sync (deploy) an application (after creation): `argocd app sync guestbook`


### CLI
Install: `brew install argocd`

## Commands
Show version: `argocd version`
Login: `argocd login --core`
