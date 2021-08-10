# BuildConfig example

Example of BuildConfig with an Inline Dockerfile.

1. Prepare
   1. Run "CRC (CodeReady Containers)" and login into `oc`
   1. Create project: `oc new-project inline-to-stream-project`
1. Build
   1. Create build config: `oc apply -f build-config.yaml`
   1. Describe build config: `oc describe bc/inline-to-stream-build-config`
   1. Start build: `oc start-build -F inline-to-stream-build-config`
   1. List builds: `oc get build`
   1. Describe build: `oc describe build/inline-to-stream-build-config-1`
   1. Describe ImageStream: `oc describe is/inline-to-stream-input-stream`
1. Application
   1. Create application: `oc apply -f app.yaml`
   1. List pods: `oc get pod`
   1. Check logs: `oc logs -f deployment/inline-to-stream-deployment`
1. Cleanup
   1. Delete application: `oc delete -f app.yaml`
   1. Delete build: `oc delete -f build-config.yaml`
