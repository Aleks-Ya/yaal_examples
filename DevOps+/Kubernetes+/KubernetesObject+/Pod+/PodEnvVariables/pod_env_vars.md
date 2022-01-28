# Craete a Pod with environment variables

1. Create: `kubectl create -f pod.yaml`
1. Show Pod logs: `kubectl logs pod-env-vars-pod`
1. Print env variables: `kubectl exec -it pod-env-vars-pod -- sh -c 'echo User=$USER; echo Pass=$PASS'`
1. Delete: `kubectl delete -f pod.yaml`
