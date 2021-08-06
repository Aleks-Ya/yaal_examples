# Image Registry

Using Docker Image Registry built-in OpenShift.

## Access to Registry from host machine
[Accessing the internal OpenShift registry](https://access.redhat.com/documentation/en-us/red_hat_codeready_containers/1.30/html/getting_started_guide/using-codeready-containers_gsg#accessing-the-internal-openshift-registry_gsg)  
[Accessing the registry](https://docs.openshift.com/container-platform/4.8/registry/accessing-the-registry.html)
[Configuring the Image Registry](https://ibm.github.io/spm-kubernetes/prereq/openshift/codeready-containers/#configuring-the-image-registry)

1. Run "CRC (CodeReady Containers)": `crc start`
1. Login to `oc` as `kubeadmin`
    1. Show credentials: `crc console --credentials`
    1. Login: `oc login -u kubeadmin -p bIKJe-pa4GI-knjWd-iicjf https://api.crc.testing:6443`
1. Login to Docker:
   1. Copy cert to trusted: 
   ```shell
   export CERT_DIR=/etc/docker/certs.d/$(oc registry info --public)
   sudo mkdir -p $CERT_DIR
   oc extract secret/router-ca -n openshift-ingress-operator --keys=tls.crt --confirm
   sudo cp tls.crt ${CERT_DIR}/ca.crt
   ```
   1. Login: `docker login -u $(oc whoami) -p $(oc whoami -t) default-route-openshift-image-registry.apps-crc.testing`
1. Push image
    1. Pull image from Docker Hub: `docker pull alpine:3`
    1. Add tag: `docker tag alpine:3 default-route-openshift-image-registry.apps-crc.testing/openshift/alpine:3`
    1. Push: `docker push default-route-openshift-image-registry.apps-crc.testing/openshift/alpine:3`

## Access from OpenShift pod
1. Create project: `oc new-project image-registry-project`
1. Deploy application: `oc apply -f image-registry-client-app.yaml`
1. Connect to pod: `oc exec -it image-registry-client-pod -- sh`
1. In pod:
   1. Add to `/etc/docker/daemon.json`: `{ "insecure-registries" : ["default-route-openshift-image-registry.apps-crc.testing"] }`
   1. Restart Docker daemon: `sudo service docker restart`
1. Login to Docker: `docker login -u kubeadmin -p bIKJe-pa4GI-knjWd-iicjf default-route-openshift-image-registry.apps-crc.testing`

## Draft
1. Run "CRC (CodeReady Containers)" and login into `oc`
1. Create project: `oc new-project image-registry-project`
#1. Create application: `oc new-app hello-world-app.yaml`
#1. Deploy application: `oc apply -f image-registry-client-app.yaml`
1. Create application: `oc new-app image-registry-client-app.yaml`
1. Check logs: `oc logs -f image-registry-client-pod`
1. Connect to pod: `oc exec -it image-registry-client-pod -- sh`

#1. Give read privileges: `oc policy add-role-to-user registry-viewer $(oc whoami)`
#1. Give write privileges: `oc policy add-role-to-user registry-editor $(oc whoami)`

1. User: `export OC_USER=developer`
1. User: `export OC_PASS=developer`
1. Give read privileges: `oc policy add-role-to-user registry-viewer $OC_USER`
1. Give write privileges: `oc policy add-role-to-user registry-editor $OC_USER`
1. Login to registry: `docker login -u $OC_USER -p $OC_PASS docker-registry.default.svc:5000`

Login from host machine:
1. Login as current user: `docker login -u $(oc whoami) -p $(oc whoami -t) default-route-openshift-image-registry.apps-crc.testing`

List images: `docker images default-route-openshift-image-registry.apps-crc.testing`

Login from pod: `docker login -u developer -p developer docker-registry.default.svc:5000`
Login from pod: `docker login -u $(oc whoami) -p $(oc whoami -t) docker-registry.default.svc:5000`


1. Delete application: `oc delete all -l app=image-registry-client`