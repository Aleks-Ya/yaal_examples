# Create a SealedSecret

1. Convert the Secret to a SealedSecret:
```
cat secret.yaml | \
kubeseal --controller-name=sealed-secrets --controller-namespace=default --format yaml \
> sealed-secret.yaml
```
2. Apply the SealedSecret to Kubernetes: `kubectl apply -f sealed-secret.yaml`
3. List SealedSecrets: `kubectl get SealedSecret`
4. Create a pod: `kubectl apply -f pod.yaml`
5. Show Pod logs: `kubectl logs sealed-secret-env-pod`
6. Print secret env variables: `kubectl exec -it sealed-secret-env-pod -- sh -c 'echo User: $USER, Password: $PASS'`
7. Delete: `kubectl delete -f pod.yaml sealed-secret.yaml`
