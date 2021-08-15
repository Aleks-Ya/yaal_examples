# Create Pod with ServiceAccount

1. Create Pod and ServiceAccount: `kubectl apply -f pod.yaml`
1. List ServiceAccounts: `kubectl get sa`
1. List Pods: `kubectl get pod`
1. Describe ServiceAccount: `kubectl describe sa my-pod-service-account`
1. Describe Pod: `kubectl describe pod service-account-pod`
1. List mounted resources: `kubectl exec -it service-account-pod -- ls -l /var/run/secrets/kubernetes.io/serviceaccount`
1. Delete ServiceAccount: `kubectl delete -f pod.yaml`
