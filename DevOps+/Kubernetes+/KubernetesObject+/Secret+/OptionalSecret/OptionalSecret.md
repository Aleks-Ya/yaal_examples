# Consume an optional Secret

Pod uses value from the 2nd secret if the 1st secret is absent.

Docs: [Optional Secrets](https://kubernetes.io/docs/concepts/configuration/secret/#restriction-secret-must-exist)

1. Create: `kubectl apply -f objects.yaml`
1. Describe Secret: `kubectl describe secret secret-optional-secret`
1. Describe Pod: `kubectl describe pod secret-optional-pod`
1. Show Pod logs: `kubectl logs secret-optional-pod`
1. Print content of secret files: `kubectl exec -it secret-optional-pod -- sh -c 'echo Password: $(cat /etc/creds/user/password.txt)'`
1. Delete: `kubectl delete -f objects.yaml`
