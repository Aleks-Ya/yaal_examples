# Create a ImageStream

1. Run "CRC (CodeReady Containers)" and login into `oc`
1. Create project: `oc new-project stream-image-project`
1. Apply deployment: `oc apply -f deployment.yaml`
1. List ImageStreams: `oc get is`
1. Describe ImageStream: `oc describe is/my-input-stream`
1. Delete ImageStream: `oc delete -f deployment.yaml`
