# ChartMuseum

## Info
ChartMuseum is a Helm Chart Repository server.
Docs: https://chartmuseum.com

## Install
### Into MiniKube as a Helm chart
1. Add a Helm repo: `helm repo add chartmuseum https://chartmuseum.github.io/charts`
2. Install a ChartMuseum chart: `helm install chartmuseum --set env.open.DISABLE_API=false chartmuseum/chartmuseum`
3. Forward port: 
```
export POD_NAME=$(kubectl get pods --namespace default -l "app.kubernetes.io/name" -o jsonpath="{.items[0].metadata.name}")
kubectl port-forward $POD_NAME 8080:8080 --namespace default
```
4. Open Web UI: `http://127.0.0.1:8080`
5. Uninstall ChartMuseum chart: `helm uninstall chartmuseum`

## REST API
Docs: https://chartmuseum.com/docs/#api
ChartMuseum version: `curl http://localhost:8080/info`
Health status: `curl http://localhost:8080/health`
List all charts: `curl http://localhost:8080/api/charts`
