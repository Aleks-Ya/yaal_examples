# AWS Elastic Container Service (ECS)

## Cluster
List clusters: `aws ecs list-clusters`

## Service
List services in a cluster: `aws ecs list-services --cluster cluster1`
Show service detials: `aws ecs describe-services --cluster cluster1 --services service1`
List service deployments: `aws ecs list-service-deployments --service arn:aws:ecs:us-east-1:523633434047:service/cluster1/service1`
Show service deployment details: `aws ecs describe-service-deployments --service-deployment-arns arn:aws:ecs:us-east-1:523633434047:service-deployment/cluster1/service1/tjxth6Tkk4Ani2lCxst11`

## Task definition
List task definitions: `aws ecs list-task-definitions`
Update task definition in a service: `aws ecs update-service --cluster cluster1 --service service1 --task-definition definition1`

## Task
List tasks in a cluster: `aws ecs list-tasks --cluster cluster1`
Show task details: `aws ecs describe-tasks --cluster cluster1 --tasks task1`
