# Consume Secret from another namespace

**Does not work: secrets are available for pods in the same namespace**

1. Create: `kubectl apply -f objects.yaml`
1. Describe Secret: `kubectl describe -n secret-namespace-1 secret secret-env-secret`
1. Describe Pod: `kubectl describe pod -n secret-namespace-2 secret-env-pod`
1. Show Pod logs: `kubectl logs -n secret-namespace-2 secret-env-pod`
1. Print secret env variables: `kubectl exec -n secret-namespace-2 -it secret-env-pod -- sh -c 'echo User: $USER, Password: $PASS'`
1. Delete: `kubectl delete -f objects.yaml`
