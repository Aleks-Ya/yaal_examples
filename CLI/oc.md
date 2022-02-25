# oc CLI (OpenShift)

Docs

[CLI Reference](https://docs.openshift.com/container-platform/3.7/cli_reference/index.html) (v3)
[OpenShift CLI (oc)](https://docs.openshift.com/container-platform/4.7/cli_reference/openshift_cli/getting-started-cli.html) (v4)

Shortcuts

bc = BuildConfig
cm = ConfigMap
is = ImageStream
sa=ServiceAccount

CLI

Run OpenShift CLI: oc
Help
- Show help: oc help
- Show help for specific command: oc help login
Login
- Show current use: oc whoami
- Login: oc login
All
- List all resources: oc get all
- List all resources with labels: oc get all --show-labels
- Delete all resources by label: oc delete all -l my-label-key=my-label-value
Project
- List projects:
	- oc projects
	- oc get projects
- Show current project: oc project
- Choose current project: oc project my_project
- Create new project:
	- Without description and display name: oc new-project my_new_project
	- With description and display name: oc new-project <project_name> --description="<description>" --display-name="<display_name>"
- Delete project: oc delete project my_project
Application
- Show app statuses: oc status
- List available templates: oc new-app -L
- Create application from template: ﻿oc new-app ahml-abm-build-config.yaml
- Run app from DockerHub image: oc new-app sonatype/nexus3:latest
- Delete application: oc delete all -l deployment=my-app-name
Pod
- List all pods: oc get pod
- List all pods (show labels): oc get pod --show-labels
- List pods with label: oc get pod -l my-label-key=my-label-value
- Describe pod: oc describe pod my-pod
- Connect by SSH: oc exec -it my-pod -- bash
- Debug pod (start container replica and connect by Bash): oc debug my-pod
Service
- List all services: oc get services
- Describe a service: oc describe service nexus3
- Expose app via a service: oc expose service nexus3
Persistent volume
- List persistent volumes: oc get pv --as system:admin
BuildConfig (bc)
- List build configs: oc get bc
- Describe a build config: oc describe bc/my-build-config
- Start a build: oc start-build my-build-config
- Start a build and follow logs: oc start-build -F my-build-config
Build (docs)
- List all builds: oc get build
- Describe a build: oc describe build my-build-1
- Cancel a build: oc cancel-build my-build-1
Sudo
- Execute a command as a Cluster Admin: oc get projects --as system:admin
RBAC
- List all cluster roles: oc describe clusterrole.rbac
- List all cluster bindings: oc describe clusterrolebinding.rbac
- List all local bindings: oc describe rolebinding.rbac
- List all role binginds: oc get rolebindings
Secret
- List secrets: oc get secrets
- Show secret content: oc get secret my_secret -o yaml
Config
- Get current namespace: oc config view --minify -o 'jsonpath={..namespace}'
ConfigMap (cm)
- List config maps: oc get cm
- Describe config map: oc describe cm/my-config-map
Logs
- Show pod logs: oc logs my-pod
- Show the last build logs: oc logs bc/my-build-config
- Show build logs: oc logs build/my-build-config-1
- Show pod logs from deployment: oc logs deployment/my-deployment
- Show last 10 lines and follow: oc logs --tail=10 -f my-pod
Namespace (as admin user)
- List namespaces: oc get namespace
- Describe namespace: oc describe namespace my-namespace
- List pods in namespace: oc get pods -n my-namespace
User
- List users: oc get user
Policy
- Add policy to user: oc policy add-role-to-user my-role my-user
- Remove policy from user: oc policy remove-role-from-user my-role my-user
Registry (Docker)
- Show Internal Registry address (for access inside OpenShift): oc registry info --internal
- Show Internal Registry address (for access ouside OpenShift): oc registry info --public
Extract
- Get CA certificate: oc extract secret/router-ca -n openshift-ingress-operator --keys=tls.crt --confirm
ImageStream (is)
- Create image stream: oc create imagestream  my-image-stream
- List all image streams: oc get is
- Describe an image stream: oc describe imagestream my-input-stream
Cluster
- List all nodes: oc get node
Account, ServiceAccount (sa)
- List Service Accounts: oc get sa
RSync
- Copy file from pod to local machine: 