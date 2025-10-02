# AWS Elastic Container Registry (ECR)

List repositories: `aws ecr describe-repositories`
List images in a repository: `aws ecr list-images --repository-name repository1`
Get password for Docker client: `aws ecr get-login-password`
Authenticate Docker client to a Registry: `aws ecr get-login-password | docker login --username AWS --password-stdin 523633434047.dkr.ecr.us-east-1.amazonaws.com`
Create a Repository: `aws ecr create-repository --repository-name repository1/hello-world`
