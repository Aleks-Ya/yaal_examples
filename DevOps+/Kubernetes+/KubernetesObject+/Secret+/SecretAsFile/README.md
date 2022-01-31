# Consume Secret as a file

Docs: [Using Secrets as files from a Pod](https://kubernetes.io/docs/concepts/configuration/secret/#using-secrets-as-files-from-a-pod)

1. Create: `kubectl apply -f objects.yaml`
1. Describe Secret: `kubectl describe secret secret-volume-secret`
1. Describe Pod: `kubectl describe pod secret-volume-pod`
1. Show Pod logs: `kubectl logs secret-volume-pod`
1. Print secret env variables: `kubectl exec -it secret-volume-pod -- sh -c 'echo User: $(cat /etc/creds/username), Password: $(cat /etc/creds/password)'`
1. Update secret at runtime: `kubectl apply -f update_secret.yaml`
1. Delete: `kubectl delete -f objects.yaml`
