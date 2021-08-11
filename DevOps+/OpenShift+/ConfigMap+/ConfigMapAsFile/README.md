# Consume ConfigMap in pod as a file

[Using ConfigMaps as files from a Pod](https://kubernetes.io/docs/concepts/configuration/configmap/#using-configmaps-as-files-from-a-pod)

1. Run "CRC (CodeReady Containers)" and login into `oc`
1. Create project: `oc new-project cm-as-file-project`
1. Create application: `oc apply -f app.yaml`
1. Check logs: `oc logs -f cm-as-file-pod`
1. Delete application: `oc delete -f app.yaml`