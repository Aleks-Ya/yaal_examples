# Create Pod living forever

Pod should wait forever and quickly stop on terminating (SIGINT #15).

1. Create Pod and ServiceAccount: `kubectl apply -f pod.yaml`
1. Describe Pod: `kubectl describe pod forever-pod`
1. Show logs: `kubectl logs -f forever-pod`
1. Delete ServiceAccount: `kubectl delete -f pod.yaml`
