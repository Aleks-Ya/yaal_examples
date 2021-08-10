# Docker ARG instruction in BuildConfig

Provide value to `ARG` Dockerfile instruction from OpenShift BuildConfig.

[Adding docker build arguments](https://docs.openshift.com/container-platform/4.8/cicd/builds/build-strategies.html#builds-strategy-docker-build-argsuments_build-strategies)

1. Prepare
   1. Run "CRC (CodeReady Containers)" and login into `oc`
   1. Create project: `oc new-project build-args-project`
1. Build
   1. Create build config: `oc apply -f build-config.yaml`
   1. Describe build config: `oc describe bc/build-args-build-config`
   1. Start build: `oc start-build -F build-args-build-config`
   1. List builds: `oc get build`
   1. Describe build: `oc describe build/build-args-build-config-1`
   1. Describe ImageStream: `oc describe is/build-args-input-stream`
1. Application
   1. Create application: `oc apply -f app.yaml`
   1. Check logs: `oc logs -f deployment/build-args-deployment`
1. Cleanup
   1. Delete application: `oc delete -f app.yaml`
   1. Delete build: `oc delete -f build-config.yaml`
