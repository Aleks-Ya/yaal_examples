# Mount several secrets to the same dir (Projected Volume)

Docs: [Configure a Pod to Use a Projected Volume for Storage](https://kubernetes.io/docs/tasks/configure-pod-container/configure-projected-volume-storage)

1. Create: `kubectl apply -f objects.yaml`
1. Show Pod logs: `kubectl logs secret-projected-volume-pod`
1. Print secret env variables: 
```
kubectl exec -it secret-projected-volume-pod -- sh -c \
	'echo User1: $(cat /etc/creds/username1), Password1: $(cat /etc/creds/password1), \
	echo User2: $(cat /etc/creds/username2), Password2: $(cat /etc/creds/password2)'
```
1. Delete: `kubectl delete -f objects.yaml`
