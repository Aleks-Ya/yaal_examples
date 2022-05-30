# helm CLI

Docs: [Helm Commands](https://helm.sh/docs/helm/)

Version

Show version:
```
helm version
```

Search

Search "kibana" chart in The Helm Hub:
```
helm search hub kibana
```
List all charts in The Helm Hub:
```
helm search hub
```
List all charts in all local repos:
```
helm search repo
```
List all charts in "stable" local repos:
```
helm search repo stable
```
Search "kibana" chart in all local repos:
```
helm search repo kibana
```

Repo

Add official stable repo:
```
helm repo add stable https://kubernetes-charts.storage.googleapis.com/
```
Update info about charts in all local repos:
```
helm repo update
```

List

List running releases:
```
helm list
helm ls
```
Run a chart:
```
helm install stable/jenkins --generate-name
```

Status

Show release status (by name):
```
helm status jenkins-1591698207
```

Uninstall

Stop a release (by name):
```
helm uninstall jenkins-1591698207
```

Create

Create new chart dir:
```
helm create mychart
```

Get

Show the manifest (all Kubernetes resources of the release):
```
helm get manifest my-release-name
```
