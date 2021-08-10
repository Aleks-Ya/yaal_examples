# Run Nginx in OpenShift

1. Run "CRC (CodeReady Containers)" and login into `oc`
1. Create project: `oc new-project nginx-project`
1. Apply deployment: `oc apply -f deployment.yaml`
1. Describe deployment: `oc describe deployment/nginx-deployment`
1. Open port for debugging: `oc port-forward deployment/nginx-deployment 8011:80`
1. Check: `curl -i http://localhost:8011`
1. Delete deployment: `oc delete -f deployment.yaml`
