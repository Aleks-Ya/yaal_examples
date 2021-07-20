# Create a Job from YAML

Create: `kubectl create -f job.yaml`  
List Job invocations: `kubectl get jobs`  
List pods of a job: `kubectl get pods -l job-name=hello-world-job`  
Delete: `kubectl delete -f job.yaml`  