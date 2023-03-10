# Projection of Secret keys to specific paths

Docs: [Projection of Secret keys to specific paths](https://kubernetes.io/docs/concepts/configuration/secret/#projection-of-secret-keys-to-specific-paths)

1. Create: `kubectl apply -f objects.yaml`
1. Describe Secret: `kubectl describe secret secret-projection-secret`
1. Describe Pod: `kubectl describe pod secret-projection-pod`
1. Show Pod logs: `kubectl logs secret-projection-pod`
1. Print content of secret files: `kubectl exec -it secret-projection-pod -- sh -c 'echo Username: $(cat /etc/creds/user/username.txt); echo Password: $(cat /etc/creds/user/password.txt); echo Key: $(cat /etc/creds/keys/key.txt)'`
1. Delete: `kubectl delete -f objects.yaml`
