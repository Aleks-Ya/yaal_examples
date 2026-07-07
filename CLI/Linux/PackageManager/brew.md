# brew CLI

Install: https://brew.sh (use VPN "US San Francisco")

## Info
Help: `brew help`
Help about a command: `brew help install`

## Commands
Show details about an app: `brew info swagger-codegen`
Disable an application without uninstalling it (if other apps depend on it): `brew unlink openjdk`

### Install
Find for an application: `brew search swagger`
List installed applications: `brew list`
Install an application: `brew install argocd`
Install several applications: `brew install pyenv pyenv-virtualenv`
Re-install an application: `brew reinstall python`

### Update
Read info about new versions (for `brew outdated`): `brew update`
List outdated apps: `brew outdated`
Upgrade all apps: `brew upgrade`
Upgrade app: `brew upgrade databricks`

### Taps (repositories)
List installed taps: `brew tap`
Tap a formula repository: `brew tap databricks/tap`

### Delete
Remove an application: `brew uninstall minikube`
