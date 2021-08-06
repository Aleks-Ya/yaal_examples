# BuildConfig example

[NOT WORKING!]

Example of BuildConfig with an Inline Dockerfile.

1. Run "CRC (CodeReady Containers)" and login into `oc`
1. Create project: `oc new-project build-config-project`
1. Create build config: `oc apply -f build-config.yaml`
1. Describe build config: `oc describe buildconfig mywebsite`
1. Start build: `oc start-build mywebsite`
1. List builds: `oc get build`
1. Describe build: `oc describe build mywebsite-1`

## Draft   
1. Create application: `oc new-app hello-world-app.yaml`
1. Check logs: `oc logs -f hello-world-pod`
1. Delete application: `oc delete all -l app=hello-world`
