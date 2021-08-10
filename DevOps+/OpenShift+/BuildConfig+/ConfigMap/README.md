# Use ConfigMap from BuildConfig

Read ConfigMap values from BuildConfig. 

1. Prepare
   1. Run "CRC (CodeReady Containers)" and login into `oc`
   1. Create project: `oc new-project config-map-project`
1. Build
   1. Create build config: `oc apply -f build-config.yaml`
   1. List config maps: `oc get cm`
   1. Describe config map: `oc describe cm/color-config-map`
   1. Describe build config: `oc describe bc/config-map-build-config`
   1. Start build: `oc start-build -F config-map-build-config`
   1. List builds: `oc get build`
   1. Describe build: `oc describe build config-map-build-config-1`
   1. Describe ImageStream: `oc describe imagestream config-map-input-stream`
1. Application
   1. Create application: `oc apply -f app.yaml`
   1. Check logs: `oc logs -f deployment/config-map-deployment`
1. Cleanup
   1. Delete application: `oc delete -f app.yaml`
   1. Delete build: `oc delete -f build-config.yaml`
