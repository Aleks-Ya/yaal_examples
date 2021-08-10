# Image BuildInput to ImageStreamTag example

Use Image BuildInput to create a Docker image in a ImageStreamTag.  
[Image source](https://docs.openshift.com/container-platform/4.8/cicd/builds/creating-build-inputs.html#builds-binary-source_creating-build-inputs)

1. Prepare
    1. Run "CRC (CodeReady Containers)" and login into `oc`
    1. Create project: `oc new-project image-to-stream-project`
1. Build
    1. Create build config: `oc apply -f build-config.yaml`
    1. Describe build config: `oc describe bc/image-to-stream-build-config`
    1. Start build: `oc start-build -F image-to-stream-build-config`
    1. List builds: `oc get build`
    1. Describe build: `oc describe build/image-to-stream-build-config-1`
    1. Describe ImageStream: `oc describe is/image-to-stream-input-stream`
1. Application
    1. Create application: `oc apply -f app.yaml`
    1. List pods: `oc get pod`
    1. Check logs: `oc logs -f deployment/image-to-stream-deployment`
1. Cleanup
    1. Delete application: `oc delete -f app.yaml`
    1. Delete build: `oc delete -f build-config.yaml`
