# kubectl CLI

Docs: [kubectl Command Reference](https://kubernetes.io/docs/reference/generated/kubectl/kubectl-commands)

## Shortcuts
- ServiceAccount = `sa`
- ConfigMap= `cm`
- service = `src`
- ReplicaSet = `rs`
- Namespace = `ns`

## Help
- Show help: `kubectl -h`
- Show help about specific command: `kubectl exec --help`

## Cluster
- Show cluster info: `kubectl cluster-info`
- List all objects: `kubectl get all`

## Node
- List all nodes: `kubectl get node`
- Describe all nodes: `kubectl describe nodes`
- Describe specific node: `kubectl describe nodes my-node`

## Namespace
- Show current namespace: `kubectl config view -o jsonpath={.contexts.context.namespace}`
- List all namespaces: `kubectl get ns`
- List namespaces with labels: `kubectl get ns --show-labels`
- Create a namespace: `kubectl create ns my-namespace`
- Delete a namespace: `kubectl delete ns my-namespace`
- List pods in a namespace: `kubectl get -n my-namespace pod`
- List pods in all namespaces: `kubectl get -A pod`
- Set current namespace: `kubectl config set-context --current --namespace=my-namespace`

## Deployment
- Show all deployments: `kubectl get deployment`
- Describe all deployments: `kubectl describe deployment`
- Describe specific desployment: `kubectl describe deployment my-deployment`
- Create deployment from YAML: `kubectl create -f my.yaml`
- Expose deployment as a service: `kubectl expose deployment my_deployment --type=NodePort`
- Delete deployment: `kubectl delete deployment hello-minikube`
- Delete deployment if not exists: `kubectl delete --ignore-not-found=true -f deployment.yaml`

## Service
- List all services: `kubectl get services`
- Run service: `kubectl run hello-minikube --image=k8s.gcr.io/echoserver:1.10 --port=8080`
- Delete service: `kubectl delete services my_service`

## ReplicaSet
- Show all ReplicaSets: `kubectl get replicaset`
- Describe all ReplicaSets: `kubectl describe replicaset`
- Describe specific ReplicaSet: `kubectl describe replicaset my-replicaset`

## Pod
- Show all pods: `kubectl get pod`
- Describe all Pods: `kubectl describe pods`
- Describe specific Pod: `kubectl describe pod my-pod`
- Get pod IP address: `kubectl get pod my_pod --template={{.status.podIP}}`
- List pod Volumes: `kubectl get pod my_pod --template={{.spec.volumes}}`
- Create pod from YAML: `kubectl create -f nginx.yaml`
- Connect to Pod with Bash: `kubectl exec -it mypod -- sh`
- Connect to specific container of a Pod with Bash: `kubectl exec -it mypod -c mycontainer -- sh`
- Get pod name by prefix:
	+ `kubectl get pod -l app=rm-spark-job-service -o name` -> `pod/rm-spark-job-service-86d9487485-cxsmv` + line break
	+ `kubectl get pod -l app=rm-spark-job-service -o jsonpath="{.items[0].metadata.name}"` -> `rm-spark-job-service-86d9487485-cxsmvaleksei`

## ForwardPort
- Forward pod (found by deployment) port 88 to localhost:8088 : `kubectl port-forward deployment/my-deployment 8088:80`

## ConfigMap
- Show all ConfigMaps: `kubectl get configmaps`
- Create ConfigMap from literals: `kubectl create configmap my-config-map --from-literal=my.key=very --from-literal=my.key2=charm`
- Create ConfigMap from YAML: `kubectl create -f /path/to/configmap.yaml`
- Create ConfigMap from dir: `kubectl create configmap my-config-map --from-file=/my/dir`
- Create ConfigMap from file: `kubectl create configmap my-config-map --from-file=/path/to/my.properties`
- Create ConfigMap from several files: `kubectl create configmap my-config-map --from-file=my1.properties --from-file=my2.properties`
- Create ConfigMap key with a whole file as a value : `kubectl create configmap my-config-map --from-file=my-key=/path/to/my.properties`
- Describe ConfigMap: `kubectl describe configmaps my-config-map`
- Show ConfigMap content as YAML: `kubectl get configmaps my-config-map -o yaml`

## Label
- Show labels for all pods: `kubectl get pods --show-labels`
- Show labels for specific pod: `kubectl get pod my_pod --show-labels`
- Set label on pod: `kubectl label pods my_pod my_label=abc`

## Config
- Show merged configuration: `kubectl config view`
- Show current context: `kubectl config current-context`
- List clusters: `kubectl config get-clusters`
- List contexts: `kubectl config get-contexts`
- Switch context: `kubectl config use-context my-context`

## Secret
- Encode secret value to Base64: `echo -n 'my_username' | base64`
- List secrets: `kubectl get secret`
- Describe secret: `kubectl describe secret my-secret`
- Create secret from files: `kubectl create secret generic my-secret --from-file=./username.txt --from-file=./password.txt`

## Log
- Show pod logs: `kubectl logs my_pod`
- Show pod logs and follow: `kubectl logs -f my_pod`
- Show last 20 lines of pod logs: `kubectl logs --tail=20 my_pod`

## Auth
- Check permission for a command: `kubectl auth can-i create pods`

## Event
- List all events: `kubectl get events`

## Credentials
- List all roles: `kubectl get role`
- List all cluster roles: `kubectl get clusterrole`

## CronJob
- List all CronJobs: `kubectl get cronjob`

## Job
- List all jobs: `kubectl get jobs`

## ServiceAccount (sa)
- List ServiceAccount: `kubectl get sa`
- Describe ServiceAccount: `kubectl describe sa my-service-account`

## Delete objects
- Delete objects from several files: `kubectl delete -f pod.yaml -f sealed-secret.yaml`
