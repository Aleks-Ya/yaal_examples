# Limit resources in BuildConfig

Get OutOfMemoryKilled build status.

[Setting build resources](https://docs.openshift.com/container-platform/4.8/cicd/builds/advanced-build-operations.html#builds-setting-build-resources_advanced-build-operations)

1. Prepare
   1. Run "CRC (CodeReady Containers)" and login into `oc`
   1. Create project: `oc new-project resource-requirements-project`
1. Build
   1. Create build config: `oc apply -f build-config.yaml`
   1. Describe build config: `oc describe bc/resource-requirements-build-config`
   1. Start build: `oc start-build -F resource-requirements-build-config`
   1. List builds: `oc get build`
   1. Describe build: `oc describe build/resource-requirements-build-config-1`
   1. Describe ImageStream: `oc describe is/resource-requirements-input-stream`
1. Application
   1. Create application: `oc apply -f app.yaml`
   1. List pods: `oc get pod`
   1. Check logs: `oc logs -f deployment/resource-requirements-deployment`
1. Cleanup
   1. Delete application: `oc delete -f app.yaml`
   1. Delete build: `oc delete -f build-config.yaml`
