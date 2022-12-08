# "SideCar Container" pattern

1. Create: `kubectl create -f sidecar.yaml`
1. Connect to container #1 with Bash: `kubectl exec -it sidecar-pod -c sidecar-container-1 -- sh`
1. Delete: `kubectl delete -f sidecar.yaml`
