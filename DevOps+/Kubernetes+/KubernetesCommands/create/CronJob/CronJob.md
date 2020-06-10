# Create a CronJob from YAML

Create: `kubectl create -f CronJob.yaml`  
List CronJobs: `kubectl get cronjob`  
List Job invocations: `kubectl get jobs`  
List pods of a job: `kubectl get pods -l job-name=hello-1591779660`  
Show logs of a pod: `kubectl logs hello-1591781700-bzqs4`  
Delete: `kubectl delete -f CronJob.yaml`  