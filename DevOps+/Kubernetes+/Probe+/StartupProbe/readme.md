# Startup Probe

Docs: https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes

Apply deployment: `kubectl apply -f deployment.yaml`

Check success deployment: `kubectl describe deployment nginx-success-deployment | grep Available`
Check fail deployment: `kubectl describe deployment nginx-fail-deployment | grep Available`

Delete deployment: `kubectl delete -f deployment.yaml`


