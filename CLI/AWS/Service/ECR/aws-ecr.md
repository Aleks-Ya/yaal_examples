# AWS Elastic Container Registry (ECR)

## Docker client
Get password for Docker client: `aws ecr get-login-password`
Authenticate Docker client to a Registry: `aws ecr get-login-password | docker login --username AWS --password-stdin 523633434047.dkr.ecr.us-east-1.amazonaws.com`

## Image
List images in a repository: `aws ecr list-images --repository-name repository1`

## Repository
List repositories: `aws ecr describe-repositories`
Create a Repository: `aws ecr create-repository --repository-name repository1/hello-world`
Delete a Repository (empty): `aws ecr delete-repository --repository-name repository1/hello-world`
Delete a Repository (not empty): `aws ecr delete-repository --force --repository-name repository1/hello-world`

## Errors

### Unexpected end of JSON input
Command: `docker --debug push 523633434047.dkr.ecr.us-east-1.amazonaws.com/kata-repo-1/hello-world:v1`
Message:
```
The push refers to repository [523633434047.dkr.ecr.us-east-1.amazonaws.com/kata-repo-push-image-to-repository-user/hello-world]
53d204b3dc5d: Pushing [==================================================>]  11.78kB
error parsing HTTP 403 response body: unexpected end of JSON input: ""
```
Cause: missing `ecr:BatchCheckLayerAvailability` permission
