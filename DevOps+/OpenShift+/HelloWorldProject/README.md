# HelloWorldProject

Hello World project for OpenShift.

1. Run "CRC (CodeReady Containers)" and login into `oc`
1. Create project: `oc new-project hello-world-project`
1. Create application: `oc new-app hello-world-app.yaml`
1. Check logs: `oc logs -f hello-world-pod`
1. Delete application: `oc delete all -l app=hello-world`