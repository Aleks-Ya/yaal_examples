# Consume Secret as environment variable

[Using Secrets as environment variables](https://kubernetes.io/docs/concepts/configuration/secret/#using-secrets-as-environment-variables)

1. Create: `kubectl apply -f objects.yaml`
1. Describe Secret: `kubectl describe secret secret-env-secret`
1. Describe Pod: `kubectl describe pod secret-env-pod`
1. Show Pod logs: `kubectl logs secret-env-pod`
1. Print secret env variables: `kubectl exec -it secret-env-pod -- sh -c 'echo User: $USER, Password: $PASS'`
1. Delete ServiceAccount: `kubectl delete -f objects.yaml`
