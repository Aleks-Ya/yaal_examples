# Create a ImageStream

1. Run "CRC (CodeReady Containers)" and login into `oc`
1. Create project: `oc new-project stream-image-project`
1. Apply deployment: `oc apply -f deployment.yaml`
1. List ImageStreams: `oc get imagestream`
1. Describe ImageStream: `oc describe imagestream my-input-stream`
1. Delete ImageStream: `oc delete -f deployment.yaml`
