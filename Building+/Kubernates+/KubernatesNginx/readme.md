# Run Nginx in Kubernates

Source: https://kubernetes.io/docs/tasks/run-application/run-stateless-application-deployment/#creating-and-exploring-an-nginx-deployment

Apply deployment: `kubectl apply -f deployment.yaml`

Describe deployment: `kubectl describe deployment nginx-deployment`

List pods: `kubectl get pods -l app=nginx`

Delete deployment: `kubectl delete deployment nginx-deployment`
