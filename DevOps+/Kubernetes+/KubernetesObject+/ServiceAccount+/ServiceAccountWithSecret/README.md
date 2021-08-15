# Attach Secret to Pod via ServiceAccount

NOT FINISHED (how to use Secrets from ServiceAccount in Pods?)

1. Create Pod and ServiceAccount: `kubectl apply -f objects.yaml`
1. Describe Secret: `kubectl describe secret sa-with-secret-secret`
1. Describe ServiceAccount: `kubectl describe sa sa-with-secret-service-account`
1. Describe Pod: `kubectl describe pod sa-with-secret-pod`
1. List mounted resources: `kubectl exec -it sa-with-secret-pod -- ls -l /var/run/secrets/kubernetes.io/serviceaccount`
1. Delete ServiceAccount: `kubectl delete -f objects.yaml`
