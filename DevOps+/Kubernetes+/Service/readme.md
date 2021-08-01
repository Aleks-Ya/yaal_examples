# Access Pods through a Service

Apply deployment: `kubectl apply -f deployment.yaml`

Describe:
1. Deployment: `kubectl describe deployment nginx-deployment`
2. Service: `kubectl describe service nginx-service`

List pods: `kubectl get pods -l app=nginx`

Open port for debugging: 
1. `kubectl port-forward service/nginx-service 8011:80`
2. Open http://localhost:8011

Delete deployment: `kubectl delete -f deployment.yaml`


