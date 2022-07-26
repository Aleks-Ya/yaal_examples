# helm CLI

Docs: [Helm Commands](https://helm.sh/docs/helm/)

## Install Helm client
 - `sudo snap install helm --classic`
 - `brew install kubernetes-helm`
 - Download TAR and unpack to PATH: https://helm.sh/docs/intro/install/

## Info
Show version: `helm version`
Show env variables: `helm env`

## Search in repo
Search "kibana" chart in The Helm Hub: `helm search hub kibana`
List all charts in The Helm Hub: `helm search hub`
List all charts in all local repos: `helm search repo`
List all charts in "stable" local repos: `helm search repo stable`
Search "kibana" chart in all local repos: `helm search repo kibana`

## Repo
Docs: https://helm.sh/docs/helm/helm_repo
List repos: `helm repo list`
Add official stable repo: `helm repo add stable https://charts.helm.sh/stable`
Add third-party repo: `helm repo add chartmuseum https://chartmuseum.github.io/charts`
Update info about charts in all local repos: `helm repo update`

## List
List running releases: `helm list`, `helm ls`

## Install a chart
Docs: https://helm.sh/docs/helm/helm_install
Run a chart (generate name): `helm install stable/jenkins --generate-name`
Run a chart (specific name): `helm install jenkins-stable stable/jenkins`

## Status
Show release status (by name): `helm status jenkins-1591698207`

## Uninstall
Stop a release (by name): `helm uninstall jenkins-1591698207`

## Create
Create new chart dir: `helm create mychart`

## Get
Show the manifest (all Kubernetes resources of the release): `helm get manifest my-release-name`

## Template
Render chart YAML files: `helm template my_chart_folder`
